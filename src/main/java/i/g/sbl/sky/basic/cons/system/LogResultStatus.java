package i.g.sbl.sky.basic.cons.system;

import i.g.sbl.sky.config.jpa.EnumBase;
import i.g.sbl.sky.config.jpa.EnumConverter;
import jakarta.persistence.Converter;

public enum LogResultStatus implements EnumBase<String> {
    success("1"), failed("0");
    private String code;

    LogResultStatus(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Converter(autoApply = true)
    public static class LogResultStatusConverter extends EnumConverter<LogResultStatus, String> {

        public LogResultStatusConverter() {
            super(LogResultStatus.class);
        }
    }
}
