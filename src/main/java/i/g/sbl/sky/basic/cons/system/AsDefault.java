package i.g.sbl.sky.basic.cons.system;

import i.g.sbl.sky.basic.jpa.EnumBase;
import i.g.sbl.sky.basic.jpa.EnumConverter;
import jakarta.persistence.Converter;

/**
 * 是否默认
 * 关联实体表: sys_dict_data
 * 关联实体字段: as_default
 */
public enum AsDefault implements EnumBase<String> {

    /**
     * 否
     */
    no("0"), 
    /**
     * 是
     */
    yes("1")
    ;

    private final String code;

    AsDefault(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Converter(autoApply = true)
    public static class AsDefaultConverter extends EnumConverter<AsDefault, String> {

        public AsDefaultConverter() {
            super(AsDefault.class);
        }
    }
}
