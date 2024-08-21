package i.g.sbl.sky.basic.cons.system;

import i.g.sbl.sky.config.jpa.EnumBase;
import i.g.sbl.sky.config.jpa.EnumConverter;
import jakarta.persistence.Converter;

public enum Type implements EnumBase<String> {

    /**
     * 认证操作
     */
    certification_operate("1"), 
    /**
     * 系统操作
     */
    system_operate("2")
    ;

    private final String code;

    Type(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Converter(autoApply = true)
    public static class TypeConverter extends EnumConverter<Type, String> {

        public TypeConverter() {
            super(Type.class);
        }
    }
}
