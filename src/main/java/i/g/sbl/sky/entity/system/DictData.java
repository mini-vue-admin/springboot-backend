package i.g.sbl.sky.entity.system;

import i.g.sbl.sky.basic.cons.system.Status;
import i.g.sbl.sky.config.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

/**
 * 字典数据表
 */
@Data
@Entity
@Table(name = "sys_dict_data")
public class DictData extends BaseEntity {

    private String dictType;

    private String dictLabel;

    private String dictValue;

    private Integer orderNum;

    private String cssClass;

    private Boolean asDefault;

    private Status status;

    public void copyNonNulls(DictData dictData) {
        Mapper.INSTANCE.map(dictData, this);
    }

    @org.mapstruct.Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public interface Mapper {
        Mapper INSTANCE = Mappers.getMapper(Mapper.class);

        void map(DictData source, @MappingTarget DictData target);
    }
}
