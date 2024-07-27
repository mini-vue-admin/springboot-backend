package i.g.sbl.sky.entity.system;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "sys_role_menu")
@Entity
@Data
public class RoleMenuRelation {
    @Id
    private Long menuId;
    @Id
    private Long roleId;
}
