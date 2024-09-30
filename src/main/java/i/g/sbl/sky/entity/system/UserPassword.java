package i.g.sbl.sky.entity.system;

import i.g.sbl.sky.basic.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DynamicUpdate
@Table(name = "sys_user")
public class UserPassword extends BaseEntity {
    private String username;
    private String password;
}
