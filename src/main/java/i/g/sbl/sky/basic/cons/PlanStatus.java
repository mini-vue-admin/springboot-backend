package i.g.sbl.sky.basic.cons;

import i.g.sbl.sky.config.jpa.EnumBase;
import i.g.sbl.sky.config.jpa.EnumConverter;
import jakarta.persistence.Converter;

public enum PlanStatus implements EnumBase<String> {
    not_start("not_start"),
    running("running"),
    pass("pass"),
    fail("fail"),
    hang("hang"),
    stopped("stopped");

    private String code;

    PlanStatus(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Converter(autoApply = true)
    public static class PlanStatusConverter  extends EnumConverter<PlanStatus, String> {

        public PlanStatusConverter() {
            super(PlanStatus.class);
        }
    }
}
