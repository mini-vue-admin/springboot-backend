package i.g.sbl.sky.entity.system;

import i.g.sbl.sky.config.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "sys_role")
public class Role extends BaseEntity {
    private String roleName;
    private String roleKey;
    private String remark;
}
