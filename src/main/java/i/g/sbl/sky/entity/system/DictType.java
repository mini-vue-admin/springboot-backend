package i.g.sbl.sky.entity.system;

import i.g.sbl.sky.config.jpa.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

/**
* 字典类型表
*/
@Data
@Entity
@Table(name = "sys_dict_type")
public class DictType extends BaseEntity {
    
    private String dictName;
    
    private String dictType;
    
    private String remark;

    public void copyNonNulls(DictType dictType) {
        Mapper.INSTANCE.map(dictType, this);
    }

    @org.mapstruct.Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public interface Mapper {
        Mapper INSTANCE = Mappers.getMapper(Mapper.class);

        void map(DictType source, @MappingTarget DictType target);
    }
}
