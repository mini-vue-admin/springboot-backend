package i.g.sbl.sky.entity.system;

import i.g.sbl.sky.basic.cons.system.Status;
import i.g.sbl.sky.basic.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

/**
 * 字典数据表
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DynamicUpdate
@Table(name = "sys_dict_data")
public class DictData extends BaseEntity {

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 字典标签
     */
    private String dictLabel;

    /**
     * 字典键值
     */
    private String dictValue;

    /**
     * 字典排序
     */
    private Integer orderNum;

    /**
     * 样式属性
     */
    private String cssClass;

    /**
     * 是否默认(否:0, 是:1)
     */
    private Boolean asDefault;

    /**
     * 状态(停用:0, 正常:1)
     */
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
