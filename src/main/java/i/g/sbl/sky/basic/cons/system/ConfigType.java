package i.g.sbl.sky.basic.cons.system;

import i.g.sbl.sky.config.jpa.EnumBase;
import i.g.sbl.sky.config.jpa.EnumConverter;
import jakarta.persistence.Converter;

/**
 * 参数类型
 * 关联实体表: sys_config
 * 关联实体字段: config_type
 */
public enum ConfigType implements EnumBase<String> {

    /**
     * 系统内置
     */
    system("0"),
    /**
     * 用户定义
     */
    custom("1")
    ;

    private final String code;

    ConfigType(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Converter(autoApply = true)
    public static class ConfigTypeConverter extends EnumConverter<ConfigType, String> {

        public ConfigTypeConverter() {
            super(ConfigType.class);
        }
    }
}
