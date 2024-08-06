package i.g.sbl.sky.entity.system;


import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

/**
* 角色用户表
*/
@Data
@Entity
@Table(name = "sys_role_user")
@IdClass(RoleUser.class)
public class RoleUser {
    @Id
    private Long userId;
    @Id
    private Long roleId;

    public void copyNonNulls(RoleUser roleUser) {
        Mapper.INSTANCE.map(roleUser, this);
    }

    @org.mapstruct.Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public interface Mapper {
        Mapper INSTANCE = Mappers.getMapper(Mapper.class);

        void map(RoleUser source, @MappingTarget RoleUser target);
    }
}
