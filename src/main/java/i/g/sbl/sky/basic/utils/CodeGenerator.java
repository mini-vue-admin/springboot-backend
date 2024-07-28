package i.g.sbl.sky.basic.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Data
public class CodeGenerator {
    public static final String[] templates = new String[]{"controller.ftl", "service.ftl", "serviceImpl.ftl", "repo.ftl", "entity.ftl"};

    @JsonIgnore
    private DataSource dataSource;
    private String basePackage = "i.g.sbl.sky";
    private String modelName = "system";
    private String tableNameRegex = "sys_.*";
    private String outputDir = "/tmp";


    @JsonIgnore
    private Configuration cfg;

    @SneakyThrows
    public CodeGenerator() {
        cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setDirectoryForTemplateLoading(ResourceUtils.getFile("classpath:templates/code-gen"));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

    }

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

                String className = tableName.substring(tableName.indexOf("_") + 1);
                root.put("ENTITY_NAME", StringUtils.capitalize(CaseUtils.toCamelCase(className)));
                root.put("ENTITY_FIELD_NAME", CaseUtils.toCamelCase(className));

                ResultSet rs = metaData.getPrimaryKeys(connection.getCatalog(), null, tableName);

                while (rs.next()) {
                    String columnName = rs.getString("COLUMN_NAME");
                    List<String> primaryKey = (List<String>) root.computeIfAbsent("PRIMARY_KEYS", (k) -> new ArrayList<String>());
                    primaryKey.add(CaseUtils.toCamelCase(columnName));
                }

                List<Map<String, Object>> fields = new ArrayList<>();
                root.put("FIELDS", fields);


                ResultSet columnsResultSet = metaData.getColumns(connection.getCatalog(), null, tableName, "%");
                while (columnsResultSet.next()) {
                    String columnName = columnsResultSet.getString("COLUMN_NAME").toLowerCase();
                    String columnType = columnsResultSet.getString("TYPE_NAME");
                    fields.add(Map.of(
                            "FIELD_NAME", CaseUtils.toCamelCase(columnName),
                            "FIELD_TYPE", CaseUtils.toCamelCase(toFieldType(columnType))

                            ));

                }
                list.add(root);
            }
        }

        return list;
    }

    private String toFieldType(String columnType) {
        System.out.println(columnType);
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

    @SneakyThrows
    public void generate() {
        List<Map<String, Object>> maps = retireMeta();
        for (Map<String, Object> root : maps) {
            for (String template : templates) {
                Template temp = cfg.getTemplate(template);
                String[] packageDir = toPackageDir(template, this.basePackage, this.modelName);
                Path dir = Path.of(this.outputDir, packageDir);
                if (!Files.exists(dir)) {
                    Files.createDirectories(dir);
                }
                try (BufferedWriter writer = Files.newBufferedWriter(dir.resolve(toClassName(template, (String) root.get("ENTITY_NAME"))))) {
                    temp.process(root, writer);
                }
            }
        }
    }

    private String[] toPackageDir(String template, String basePackage, String modelName) {
        String pkg = switch (template) {
            case "controller.ftl" -> "controller";
            case "service.ftl" -> "service";
            case "serviceImpl.ftl" -> "service";
            case "repo.ftl" -> "repo";
            case "entity.ftl" -> "entity";
            default -> throw new IllegalStateException("Unexpected value: " + template);
        };
        if (template.equals("serviceImpl.ftl")) {
            return ArrayUtils.addAll(basePackage.split("\\."), pkg, modelName, "impl");
        } else {
            return ArrayUtils.addAll(basePackage.split("\\."), pkg, modelName);
        }
    }

    private String toClassName(String template, String entityName) {
        return switch (template) {
            case "controller.ftl" -> entityName + "Controller.java";
            case "service.ftl" -> entityName + "Service.java";
            case "serviceImpl.ftl" -> entityName + "ServiceImpl.java";
            case "repo.ftl" -> entityName + "Repo.java";
            case "entity.ftl" -> entityName + ".java";
            default -> throw new IllegalStateException("Unexpected value: " + template);
        };
    }
}
