package i.g.sbl.sky.basic.cons.system;

import i.g.sbl.sky.config.jpa.EnumBase;
import i.g.sbl.sky.config.jpa.EnumConverter;
import jakarta.persistence.Converter;

public enum LogLevel implements EnumBase<String> {
    normal("1"), warn("2"), danger("3");
    private String code;

    LogLevel(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Converter(autoApply = true)
    public static class LogLevelStatusConverter extends EnumConverter<LogLevel, String> {

        public LogLevelStatusConverter() {
            super(LogLevel.class);
        }
    }
}
