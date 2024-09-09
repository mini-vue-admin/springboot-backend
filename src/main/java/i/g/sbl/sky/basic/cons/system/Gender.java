package i.g.sbl.sky.basic.cons.system;

import i.g.sbl.sky.basic.jpa.EnumBase;
import i.g.sbl.sky.basic.jpa.EnumConverter;
import jakarta.persistence.Converter;

/**
 * 用户性别
 * 关联实体表: sys_user
 * 关联实体字段: gender
 */
public enum Gender implements EnumBase<String> {

    /**
     * 男
     */
    male("M"), 
    /**
     * 女
     */
    female("F"), 
    /**
     * 未知
     */
    unknown("U")
    ;

    private final String code;

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
