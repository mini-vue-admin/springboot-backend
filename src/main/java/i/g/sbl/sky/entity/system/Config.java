package i.g.sbl.sky.entity.system;

import i.g.sbl.sky.basic.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import i.g.sbl.sky.basic.cons.system.ConfigType;

/**
* 参数配置表
*/
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "sys_config")
public class Config extends BaseEntity {

    /**
     * 参数名称
     */
    private String configName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 参数键名
     */
    private String configKey;

    /**
     * 参数键值
     */
    private String configValue;

    /**
     * 参数类型(系统内置:0, 用户定义:1)
     */
    private ConfigType configType;

    public void copyNonNulls(Config config) {
        Mapper.INSTANCE.map(config, this);
    }

    @org.mapstruct.Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public interface Mapper {
        Mapper INSTANCE = Mappers.getMapper(Mapper.class);

        void map(Config source, @MappingTarget Config target);
    }
}
