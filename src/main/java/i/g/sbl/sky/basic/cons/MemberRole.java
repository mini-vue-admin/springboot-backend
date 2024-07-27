package i.g.sbl.sky.basic.cons;

import i.g.sbl.sky.config.jpa.EnumBase;
import i.g.sbl.sky.config.jpa.EnumConverter;
import jakarta.persistence.Converter;

public enum MemberRole implements EnumBase<String> {
    admin("admin"), dev("dev"), viewer("viewer");

    private String code;

    MemberRole(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }


    @Converter(autoApply = true)
    public static class MemberRoleConverter extends EnumConverter<MemberRole, String> {

        public MemberRoleConverter() {
            super(MemberRole.class);
        }
    }
}
