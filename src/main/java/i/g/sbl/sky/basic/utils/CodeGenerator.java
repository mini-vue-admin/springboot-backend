package i.g.sbl.sky.basic.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import i.g.sbl.translate.CnTranslator;
import i.g.sbl.translate.Language;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.OrderedMap;
import org.apache.commons.collections4.map.ListOrderedMap;
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
import java.util.*;
import java.util.regex.Pattern;

@Slf4j
@Data
public class CodeGenerator {
    public static final String[] crud_templates = new String[]{"controller.ftl", "service.ftl", "serviceImpl.ftl", "repo.ftl", "entity.ftl"};
    public static final String enum_template = "enum.ftl";
    public static final String[] base_entity_columns = new String[]{"id", "create_time", "create_by", "update_time", "update_by"};

    @JsonIgnore
    private DataSource dataSource;

    @Schema( type = "string", example = "i.g.sbl.sky")
    private String basePackage = "i.g.sbl.sky";

    @Schema( type = "string", example = "system")
    private String moduleName = "system";

    @Schema( type = "string", example = "sys_.*")
    private String tableNameRegex = "sys_.*";

    @Schema( type = "string", example = "/tmp")
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
     * @return  每张表，作为一条记录
     */
    @SneakyThrows
    public List<Map<String, Object>> retireMeta() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();


        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tablesResultSet = metaData.getTables(connection.getCatalog(), null, "%", new String[]{"TABLE"});
            while (tablesResultSet.next()) {
                HashMap<String, Object> root = new HashMap<>();
                root.put("ENTITY_PACKAGE_IMPORTS", new HashSet<>());

                String tableName = tablesResultSet.getString("TABLE_NAME").toLowerCase();
                if (!Pattern.compile(this.tableNameRegex).matcher(tableName).matches()) {
                    continue;
                }
                root.put("TABLE_NAME", tableName);

                String shortTableName = removeTableNamePrefix && tableName.indexOf("_") > 0 ? tableName.substring(tableName.indexOf("_") + 1) : tableName;
                root.put("ENTITY_NAME", StringUtils.capitalize(CaseUtils.toCamelCase(shortTableName)));
                root.put("ENTITY_FIELD_NAME", CaseUtils.toCamelCase(shortTableName));

                String tableComment = getTableComment(connection, tableName);
                tableComment = StringUtils.hasText(tableComment) ? tableComment : tableName;
                root.put("TABLE_COMMENT", tableComment);

                ResultSet rs = metaData.getPrimaryKeys(connection.getCatalog(), null, tableName);

                List<String> primaryKey = new ArrayList<>();
                while (rs.next()) {
                    String columnName = rs.getString("COLUMN_NAME").toLowerCase();
                    primaryKey.add(columnName);
                }

                List<Map<String, Object>> fields = new ArrayList<>();
                root.put("FIELDS", fields);

                Map<String, String> fieldComments = getFieldComments(connection, tableName);
                root.put("EXTENDS_BASE_ENTITY", fieldComments.containsKey("id") && fieldComments.containsKey("create_time"));

                ResultSet columnsResultSet = metaData.getColumns(connection.getCatalog(), null, tableName, "%");
                while (columnsResultSet.next()) {
                    String columnName = columnsResultSet.getString("COLUMN_NAME").toLowerCase();
                    if ((Boolean) root.get("EXTENDS_BASE_ENTITY")) {
                        if (Arrays.stream(base_entity_columns).anyMatch(it -> it.equals(columnName))) {
                            continue;
                        }
                    }

                    String columnType = columnsResultSet.getString("TYPE_NAME");
                    String fieldType = toFieldType(columnType);
                    if (fieldType.equals("LocalDateTime")) {
                        Set<String> pkgs = (Set<String>) root.get("ENTITY_PACKAGE_IMPORTS");
                        pkgs.add("java.time.LocalDateTime");
                    }
                    Map<String, Object> fieldDefine = new HashMap<>(Map.of(
                            "FIELD_NAME", CaseUtils.toCamelCase(columnName),
                            "FIELD_TYPE", fieldType,
                            "PRIMARY_KEY", primaryKey.contains(columnName),
                            "FIELD_COMMENT", fieldComments.get(columnName),
                            "IS_ENUM", hasEnumDefine(fieldComments.get(columnName))
                    ));

                    if (hasEnumDefine(fieldComments.get(columnName))) {
                        Map<String, Object> enumDefine = new HashMap<>();
                        enumDefine.put("ENUM_NAME", StringUtils.capitalize(CaseUtils.toCamelCase(columnName)));
                        enumDefine.put("ENUM_ITEMS", getEnumItems(fieldComments.get(columnName)));
                        enumDefine.put("ENUM_PACKAGE", this.basePackage + ".basic.cons." + moduleName);

                        fieldDefine.put("ENUM_DEFINE", enumDefine);
                        List<Map<String, ?>> enumList = (List<Map<String, ?>>) root.computeIfAbsent("ENUMS", k -> new ArrayList<>());
                        enumList.add(enumDefine);
                        Set<String> pkgs = (Set<String>) root.get("ENTITY_PACKAGE_IMPORTS");
                        pkgs.add(enumDefine.get("ENUM_PACKAGE") + "." + enumDefine.get("ENUM_NAME"));
                    }
                    if (primaryKey.contains(columnName)) {
                        List<Map<String, Object>> primaryKeys =(List<Map<String, Object>>) root.computeIfAbsent("ENTITY_PRIMARY_KEYS", k -> new ArrayList<>());
                        primaryKeys.add(fieldDefine);
                    }
                    fields.add(fieldDefine);
                }
                list.add(root);
            }
        }
        return list;
    }

    /**
     * 根据字段注释判断是否为枚举
     *
     * @param comment 字段注释
     * @return
     */
    private boolean hasEnumDefine(String comment) {
        return comment.contains("(") && comment.contains(")");
    }

    /**
     * 获取枚举项字典
     *
     * @param comment 字段注释
     * @return 枚举字典，枚举注释->枚举值
     */
    private List<Map<String, String>> getEnumItems(String comment) {
        CnTranslator translator = new CnTranslator();
        List<Map<String, String>> list = new ArrayList<>();

        String content = comment.substring(comment.indexOf("(") + 1, comment.lastIndexOf(")"));
        String[] items = content.split(",");
        for (String item : items) {
            OrderedMap<String, String> enumItem = new ListOrderedMap<String, String>();
            String[] split = item.split(":");
            enumItem.put("ITEM_COMMENT", split[0].trim());
            enumItem.put("ITEM_VALUE", CaseUtils.toCamelCase(split[1].trim()));
            enumItem.put("ITEM_NAME", translator.translate(split[0].trim(), Language.en).trim().replaceAll("\s+", "_"));
            list.add(enumItem);
        }
        return list;
    }


    @SneakyThrows
    public void generate() {
        List<Map<String, Object>> maps = retireMeta();

        log.info("Parse generator meta: {}", JsonUtils.toPrettyJson(maps));

        for (Map<String, Object> root : maps) {
            root.put("MODULE_NAME", moduleName);
            root.put("BASE_PACKAGE", basePackage);

            for (String template : crud_templates) {
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

            List<Map<String, ?>>  enums = (List<Map<String, ?>>) root.get("ENUMS");
            if (enums != null) {
                for (Map<String, ?> anEnum : enums) {
                    Template temp = cfg.getTemplate(enum_template);
                    String enumPackage = (String) anEnum.get("ENUM_PACKAGE");
                    Path dir = Path.of(this.outputDir, enumPackage.split("\\."));
                    if (!Files.exists(dir)) {
                        Files.createDirectories(dir);
                    }
                    Path classFilePath = dir.resolve(anEnum.get("ENUM_NAME") + ".java");
                    try (BufferedWriter writer = Files.newBufferedWriter(classFilePath)) {
                        temp.process(anEnum, writer);
                    }
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
