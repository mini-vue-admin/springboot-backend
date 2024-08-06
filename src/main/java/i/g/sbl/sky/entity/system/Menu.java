package i.g.sbl.sky.entity.system;

import i.g.sbl.sky.basic.cons.system.Status;
import i.g.sbl.sky.config.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

/**
* 菜单表
*/
@Data
@Entity
@Table(name = "sys_menu")
public class Menu extends BaseEntity {

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 菜单标题
     */
    private String menuTitle;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单类型(M目录 C菜单 F按钮)
     */
    private String menuType;

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
     * 菜单状态(1正常 0停用)
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
