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

    private Long parentId;

    private String menuTitle;

    private String menuName;

    private String menuType;

    private Integer orderNum;

    private String path;

    private String component;

    private String query;

    private String icon;

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
