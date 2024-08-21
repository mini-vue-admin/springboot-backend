package i.g.sbl.sky.basic.cons.system;

import i.g.sbl.sky.config.jpa.EnumBase;
import i.g.sbl.sky.config.jpa.EnumConverter;
import jakarta.persistence.Converter;

public enum Status implements EnumBase<String> {

    /**
     * 停用
     */
    disabled("0"),
    /**
     * 正常
     */
    enabled("1")
    ;

    private final String code;

    Status(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Converter(autoApply = true)
    public static class StatusConverter extends EnumConverter<Status, String> {

        public StatusConverter() {
            super(Status.class);
        }
    }
}
