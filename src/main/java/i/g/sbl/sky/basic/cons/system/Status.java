package i.g.sbl.sky.basic.cons.system;

import i.g.sbl.sky.basic.jpa.EnumBase;
import i.g.sbl.sky.basic.jpa.EnumConverter;
import jakarta.persistence.Converter;

/**
 * 帐号状态
 * 关联实体表: sys_user
 * 关联实体字段: status
 */
public enum Status implements EnumBase<String> {

    /**
     * 停用
     */
    disabled("0"),
    /**
     * 正常
     */
    enabled("1")
    ;

    private final String code;

    Status(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Converter(autoApply = true)
    public static class StatusConverter extends EnumConverter<Status, String> {

        public StatusConverter() {
            super(Status.class);
        }
    }
}
