package i.g.sbl.sky.basic.cons.system;

import i.g.sbl.sky.config.jpa.EnumBase;
import i.g.sbl.sky.config.jpa.EnumConverter;
import jakarta.persistence.Converter;

public enum Status implements EnumBase<Integer> {

    enabled(1), disabled(0);

    private final int code;

    Status(int code) {
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Converter(autoApply = true)
    public static class StatusConverter extends EnumConverter<Status, Integer> {

        public StatusConverter() {
            super(Status.class);
        }
    }
}
