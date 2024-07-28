package i.g.sbl.sky.entity.system;

import i.g.sbl.sky.basic.cons.ConfigType;
import i.g.sbl.sky.config.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Data
@Entity
@Table(name = "sys_config")
public class Config extends BaseEntity {
    private String configName;
    private String configKey;
    private String configValue;
    private ConfigType configType;
    private String remark;

    public void copyNonNulls(Config config) {
        Mapper.INSTANCE.map(config, this);
    }

    @org.mapstruct.Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public interface Mapper {
        Mapper INSTANCE = Mappers.getMapper(Config.Mapper.class);

        void map(Config source, @MappingTarget Config target);
    }
}
