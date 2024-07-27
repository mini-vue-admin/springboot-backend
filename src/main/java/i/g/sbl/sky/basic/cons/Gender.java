package i.g.sbl.sky.basic.cons;

import i.g.sbl.sky.config.jpa.EnumBase;
import i.g.sbl.sky.config.jpa.EnumConverter;
import jakarta.persistence.Converter;

public enum Gender implements EnumBase<String> {
    female("F"), male("M"), unknown("U");

    private String code;

    Gender(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Converter(autoApply = true)
    public static class GenderConverter extends EnumConverter<Gender, String> {

        public GenderConverter() {
            super(Gender.class);
        }
    }
}
