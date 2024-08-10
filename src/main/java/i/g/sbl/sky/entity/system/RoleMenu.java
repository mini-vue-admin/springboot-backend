package i.g.sbl.sky.entity.system;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

/**
 * 角色菜单表
 */
@Data
@Entity
@Table(name = "sys_role_menu")
@IdClass(RoleMenu.RoleMenuId.class)
public class RoleMenu {

    /**
     * 菜单id
     */
    @Id
    private Long menuId;

    /**
     * 角色id
     */
    @Id
    private Long roleId;

    public void copyNonNulls(RoleMenu roleMenu) {
        Mapper.INSTANCE.map(roleMenu, this);
    }

    @org.mapstruct.Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public interface Mapper {
        Mapper INSTANCE = Mappers.getMapper(Mapper.class);

        void map(RoleMenu source, @MappingTarget RoleMenu target);
    }

    @Data
    public static class RoleMenuId {
        private Long menuId;
        private Long roleId;
    }
}
