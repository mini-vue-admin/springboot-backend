package i.g.sbl.sky.entity.system;

import i.g.sbl.sky.config.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import i.g.sbl.sky.basic.cons.system.Gender;
import i.g.sbl.sky.basic.cons.system.Status;

/**
* 用户表
*/
@Data
@Entity
@Table(name = "sys_user")
public class User extends BaseEntity {

    /**
     * 用户账号
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 用户性别(男:M, 女:F, 未知:U)
     */
    private Gender gender;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 密码
     */
    private String password;

    /**
     * 帐号状态(停用:0, 正常:1)
     */
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
