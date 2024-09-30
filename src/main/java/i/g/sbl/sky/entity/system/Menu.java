package i.g.sbl.sky.entity.system;

import i.g.sbl.sky.basic.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import i.g.sbl.sky.basic.cons.system.Status;
import i.g.sbl.sky.basic.cons.system.MenuType;

/**
* 菜单表
*/
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "sys_menu")
public class Menu extends BaseEntity {

    /**
     * 父菜单ID
     */
    private String parentId;

    /**
     * 菜单标题
     */
    private String menuTitle;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单类型(目录:M, 菜单:C, 按钮:F)
     */
    private MenuType menuType;

    /**
     * 显示排序
     */
    private Integer orderNum;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 路由参数
     */
    private String query;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 菜单状态(停用:0, 正常:1)
     */
    private Status status;

    public void copyNonNulls(Menu menu) {
        Mapper.INSTANCE.map(menu, this);
    }

    @org.mapstruct.Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public interface Mapper {
        Mapper INSTANCE = Mappers.getMapper(Mapper.class);

        void map(Menu source, @MappingTarget Menu target);
    }
}
