package i.g.sbl.sky.entity.system;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "sys_role_user")
@Entity
@Data
public class RoleMember {
    @Id
    private Long userId;
    @Id
    private Long roleId;
}
