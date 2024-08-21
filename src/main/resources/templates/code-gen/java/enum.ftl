package ${ENUM_PACKAGE};

import i.g.sbl.sky.config.jpa.EnumBase;
import i.g.sbl.sky.config.jpa.EnumConverter;
import jakarta.persistence.Converter;

public enum ${ENUM_NAME} implements EnumBase<String> {

    <#list ENUM_ITEMS as item>/**
     * ${item.ITEM_COMMENT}
     */
    ${item.ITEM_NAME}("${item.ITEM_VALUE}")<#sep>, </#sep>
    </#list>;

    private final String code;

    ${ENUM_NAME}(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Converter(autoApply = true)
    public static class ${ENUM_NAME}Converter extends EnumConverter<${ENUM_NAME}, String> {

        public ${ENUM_NAME}Converter() {
            super(${ENUM_NAME}.class);
        }
    }
}
