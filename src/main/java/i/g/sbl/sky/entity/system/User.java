package i.g.sbl.sky.entity.system;

import i.g.sbl.sky.basic.cons.Gender;
import i.g.sbl.sky.basic.cons.Status;
import i.g.sbl.sky.config.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "sys_user")
public class User extends BaseEntity {

    private String username;
    private String password;
    private String nickname;
    private String email;
    private String phone;
    private String avatar;
    private Gender gender;
    private Status status;
}
