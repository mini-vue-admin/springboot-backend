package i.g.sbl.sky.basic.cons.system;

import i.g.sbl.sky.config.jpa.EnumBase;
import i.g.sbl.sky.config.jpa.EnumConverter;
import jakarta.persistence.Converter;

public enum LogType implements EnumBase<String> {
    auth("1"), sys("2");
    private String code;

    LogType(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Converter(autoApply = true)
    public static class LogTypeConverter extends EnumConverter<LogType, String> {

        public LogTypeConverter() {
            super(LogType.class);
        }
    }
}
