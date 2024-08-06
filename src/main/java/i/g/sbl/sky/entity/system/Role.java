package i.g.sbl.sky.entity.system;

import i.g.sbl.sky.config.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

/**
* 角色表
*/
@Data
@Entity
@Table(name = "sys_role")
public class Role extends BaseEntity {

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色
     */
    private String roleKey;

    /**
     * 备注
     */
    private String remark;

    public void copyNonNulls(Role role) {
        Mapper.INSTANCE.map(role, this);
    }

    @org.mapstruct.Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public interface Mapper {
        Mapper INSTANCE = Mappers.getMapper(Mapper.class);

        void map(Role source, @MappingTarget Role target);
    }
}
