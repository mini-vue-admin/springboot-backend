package i.g.sbl.sky.basic.cons;

import i.g.sbl.sky.config.jpa.EnumBase;
import i.g.sbl.sky.config.jpa.EnumConverter;
import jakarta.persistence.Converter;

public enum ConfigType implements EnumBase<String> {

    system("0"), custom("1");

    private String code;

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
