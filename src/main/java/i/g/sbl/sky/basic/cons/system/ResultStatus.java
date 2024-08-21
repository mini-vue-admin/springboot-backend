package i.g.sbl.sky.basic.cons.system;

import i.g.sbl.sky.config.jpa.EnumBase;
import i.g.sbl.sky.config.jpa.EnumConverter;
import jakarta.persistence.Converter;

public enum ResultStatus implements EnumBase<String> {

    /**
     * 失败
     */
    fail("0"), 
    /**
     * 成功
     */
    success("1")
    ;

    private final String code;

    ResultStatus(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Converter(autoApply = true)
    public static class ResultStatusConverter extends EnumConverter<ResultStatus, String> {

        public ResultStatusConverter() {
            super(ResultStatus.class);
        }
    }
}
