package i.g.sbl.sky.entity.system;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "sys_role_menu")
@Entity
@Data
@IdClass(RoleMenu.class)
public class RoleMenu {
    @Id
    private Long menuId;
    @Id
    private Long roleId;
}
