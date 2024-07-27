package i.g.sbl.sky.basic.cons;

import i.g.sbl.sky.config.jpa.EnumBase;
import i.g.sbl.sky.config.jpa.EnumConverter;
import jakarta.persistence.Converter;

public enum PlanType implements EnumBase<String> {
    simple("simple");

    private String code;

    PlanType(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Converter(autoApply = true)
    public static class PlanTypeConverter  extends EnumConverter<PlanType, String> {

        public PlanTypeConverter() {
            super(PlanType.class);
        }
    }

}
