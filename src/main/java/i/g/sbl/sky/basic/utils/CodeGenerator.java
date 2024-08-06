package i.g.sbl.sky.basic.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Slf4j
@Data
public class CodeGenerator {
    public static final String[] templates = new String[]{"controller.ftl", "service.ftl", "serviceImpl.ftl", "repo.ftl", "entity.ftl"};

    @JsonIgnore
    private DataSource dataSource;
    private String basePackage = "i.g.sbl.sky";
    private String moduleName = "system";
    private String tableNameRegex = "sys_.*";
    private String outputDir = "/tmp";

    /**
     * 是否移除表名前缀作为类名
     */
    private boolean removeTableNamePrefix = true;

    @JsonIgnore
    private Configuration cfg;

    @SneakyThrows
    public CodeGenerator() {
        cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setDirectoryForTemplateLoading(ResourceUtils.getFile("classpath:templates/code-gen/java"));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

    }

    /**
     * 从数据库中提取匹配的表，并获取所有元信息
     *
     * @return
     */
    @SneakyThrows
    public List<Map<String, Object>> retireMeta() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();


        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tablesResultSet = metaData.getTables(connection.getCatalog(), null, "%", new String[]{"TABLE"});
            while (tablesResultSet.next()) {
                HashMap<String, Object> root = new HashMap<>();
                String tableName = tablesResultSet.getString("TABLE_NAME").toLowerCase();
                if (!Pattern.compile(this.tableNameRegex).matcher(tableName).matches()) {
                    continue;
                }
                root.put("TABLE_NAME", tableName);

                String shortTableName = removeTableNamePrefix && tableName.indexOf("_") > 0 ? tableName.substring(tableName.indexOf("_") + 1) : tableName;
                root.put("ENTITY_NAME", StringUtils.capitalize(CaseUtils.toCamelCase(shortTableName)));
                root.put("ENTITY_FIELD_NAME", CaseUtils.toCamelCase(shortTableName));
                root.put("TABLE_COMMENT", getTableComment(connection, tableName));

                ResultSet rs = metaData.getPrimaryKeys(connection.getCatalog(), null, tableName);

                List<String> primaryKey = new ArrayList<>();
                while (rs.next()) {
                    String columnName = rs.getString("COLUMN_NAME").toLowerCase();
                    primaryKey.add(columnName);
                }

                List<Map<String, Object>> fields = new ArrayList<>();
                root.put("FIELDS", fields);

                Map<String, String> fieldComments = getFieldComments(connection, tableName);
                ResultSet columnsResultSet = metaData.getColumns(connection.getCatalog(), null, tableName, "%");
                while (columnsResultSet.next()) {
                    String columnName = columnsResultSet.getString("COLUMN_NAME").toLowerCase();
                    String columnType = columnsResultSet.getString("TYPE_NAME");
                    fields.add(Map.of(
                            "FIELD_NAME", CaseUtils.toCamelCase(columnName),
                            "FIELD_TYPE", toFieldType(columnType),
                            "PRIMARY_KEY", primaryKey.contains(columnName),
                            "FIELD_COMMENT", fieldComments.get(columnName)
                    ));

                }
                list.add(root);
            }
        }
        return list;
    }



    @SneakyThrows
    public void generate() {
        List<Map<String, Object>> maps = retireMeta();
        log.info(JsonUtils.toPrettyJson(maps));
        for (Map<String, Object> root : maps) {
            root.put("MODULE_NAME", moduleName);
            root.put("BASE_PACKAGE", basePackage);

            for (String template : templates) {
                Template temp = cfg.getTemplate(template);
                String[] packageDir = toPackages(template, this.basePackage, this.moduleName);
                root.put("MODULE_PACKAGE", String.join(".", packageDir));
                Path dir = Path.of(this.outputDir, packageDir);
                if (!Files.exists(dir)) {
                    Files.createDirectories(dir);
                }
                String className = toClassName(template, (String) root.get("ENTITY_NAME"));
                Path classFilePath = dir.resolve(className + ".java");
                try (BufferedWriter writer = Files.newBufferedWriter(classFilePath)) {
                    temp.process(root, writer);
                }
            }
        }
    }

    /**
     * 获取字段注释
     *
     * @param connection
     * @param tableName
     * @return
     */
    @SneakyThrows
    private Map<String, String> getFieldComments(Connection connection, String tableName) {
        Map<String, String> map = new HashMap<>();
        try (Statement stmt = connection.createStatement();) {
            String schema = connection.getCatalog();
            ResultSet rs = stmt.executeQuery(STR."""
            SELECT COLUMN_NAME, COLUMN_COMMENT FROM INFORMATION_SCHEMA.COLUMNS  WHERE TABLE_SCHEMA = '\{schema}' AND TABLE_NAME = '\{tableName}'
            """);
            while (rs.next()) {
                map.put(rs.getString("COLUMN_NAME").toLowerCase(), rs.getString("COLUMN_COMMENT"));
            }
        }
        return map;
    }

    /**
     * 获取表注释
     *
     * @param connection
     * @param tableName
     * @return
     */
    @SneakyThrows
    private String getTableComment(Connection connection, String tableName) {
        try (Statement stmt = connection.createStatement();) {
            String schema = connection.getCatalog();
            ResultSet rs = stmt.executeQuery(STR."""
            SELECT TABLE_COMMENT FROM INFORMATION_SCHEMA.TABLES  WHERE TABLE_SCHEMA = '\{schema}' AND TABLE_NAME = '\{tableName}'
            """);
            if (rs != null && rs.next()) {
                String comment = rs.getString(1);
                return comment;
            }
        }
        return "";
    }

    /**
     * 根据数据库字段类型，生成对应的java类型
     *
     * @param columnType
     * @return
     */
    private String toFieldType(String columnType) {
        return switch (columnType) {
            case "VARCHAR", "CHAR" -> "String";
            case "DATETIME" -> "LocalDateTime";
            case "INT" -> "Integer";
            case "LONG", "BIGINT" -> "Long";
            case "FLOAT" -> "Float";
            case "DOUBLE" -> "Double";
            case "BIT" -> "Boolean";
            default -> "String";
        };
    }

    /**
     * 根据模板名称和基础包和模块名称，生成目标模板类的包名列表
     *
     * @param template
     * @param basePackage
     * @param moduleName
     * @return
     */
    private String[] toPackages(String template, String basePackage, String moduleName) {

        String type = switch (template) {
            case "controller.ftl" -> "controller";
            case "service.ftl", "serviceImpl.ftl" -> "service";
            case "repo.ftl" -> "repo";
            case "entity.ftl" -> "entity";
            default -> throw new IllegalStateException("Unexpected value: " + template);
        };
        String packages = String.join(".", basePackage, type, template.equals("serviceImpl.ftl") ? moduleName + ".impl" : moduleName);
        return packages.split("\\.");
    }

    /**
     * 根据模板类型和实体类名生成目标类名
     *
     * @param template
     * @param entityName
     * @return
     */
    private String toClassName(String template, String entityName) {
        return switch (template) {
            case "controller.ftl" -> entityName + "Controller";
            case "service.ftl" -> entityName + "Service";
            case "serviceImpl.ftl" -> entityName + "ServiceImpl";
            case "repo.ftl" -> entityName + "Repo";
            case "entity.ftl" -> entityName;
            default -> throw new IllegalStateException("Unexpected value: " + template);
        };
    }
}
