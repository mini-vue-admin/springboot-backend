package i.g.sbl.sky.basic.cons.system;

import i.g.sbl.sky.basic.jpa.EnumBase;
import i.g.sbl.sky.basic.jpa.EnumConverter;
import jakarta.persistence.Converter;

/**
 * 危险级别
 * 关联实体表: sys_log
 * 关联实体字段: level
 */
public enum Level implements EnumBase<String> {

    /**
     * 正常
     */
    normal("1"),
    /**
     * 警告
     */
    warn("2"), 
    /**
     * 危险
     */
    danger("3")
    ;

    private final String code;

    Level(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Converter(autoApply = true)
    public static class LevelConverter extends EnumConverter<Level, String> {

        public LevelConverter() {
            super(Level.class);
        }
    }
}
