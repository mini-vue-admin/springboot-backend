package i.g.sbl.sky.basic.cons.system;

import i.g.sbl.sky.basic.jpa.EnumBase;
import i.g.sbl.sky.basic.jpa.EnumConverter;
import jakarta.persistence.Converter;

/**
 * 操作类型
 * 关联实体表: sys_log
 * 关联实体字段: type
 */
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
