package i.g.sbl.sky.entity.system;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

/**
 * 角色用户表
 */
@Data
@Entity
@Table(name = "sys_role_user")
@IdClass(RoleUser.RoleUserId.class)
@NoArgsConstructor
@AllArgsConstructor
public class RoleUser {

    /**
     * 用户id
     */
    @Id
    private String userId;

    /**
     * 角色id
     */
    @Id
    private String roleId;

    public void copyNonNulls(RoleUser roleUser) {
        Mapper.INSTANCE.map(roleUser, this);
    }

    @org.mapstruct.Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public interface Mapper {
        Mapper INSTANCE = Mappers.getMapper(Mapper.class);

        void map(RoleUser source, @MappingTarget RoleUser target);
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class RoleUserId {

        /**
         * 用户id
         */
        private String userId;

        /**
         * 角色id
         */
        private String roleId;
    }
}
