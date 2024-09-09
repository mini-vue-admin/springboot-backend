package i.g.sbl.sky.basic.cons.system;

import i.g.sbl.sky.basic.jpa.EnumBase;
import i.g.sbl.sky.basic.jpa.EnumConverter;
import jakarta.persistence.Converter;

/**
 * 菜单类型
 * 关联实体表: sys_menu
 * 关联实体字段: menu_type
 */
public enum MenuType implements EnumBase<String> {

    /**
     * 目录
     */
    dir("M"),
    /**
     * 菜单
     */
    menu("C"), 
    /**
     * 按钮
     */
    button("F")
    ;

    private final String code;

    MenuType(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Converter(autoApply = true)
    public static class MenuTypeConverter extends EnumConverter<MenuType, String> {

        public MenuTypeConverter() {
            super(MenuType.class);
        }
    }
}
