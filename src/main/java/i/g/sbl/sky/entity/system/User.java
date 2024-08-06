package i.g.sbl.sky.entity.system;

import i.g.sbl.sky.basic.cons.system.Gender;
import i.g.sbl.sky.basic.cons.system.Status;
import i.g.sbl.sky.config.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

/**
 * 用户表
 */
@Data
@Entity
@Table(name = "sys_user")
public class User extends BaseEntity {

    private String username;

    private String nickname;

    private String email;

    private String phone;

    private Gender gender;

    private String avatar;

    private String password;

    private Status status;

    public void copyNonNulls(User user) {
        Mapper.INSTANCE.map(user, this);
    }

    @org.mapstruct.Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public interface Mapper {
        Mapper INSTANCE = Mappers.getMapper(Mapper.class);

        void map(User source, @MappingTarget User target);
    }
}
