package i.g.sbl.sky.entity.system;

import i.g.sbl.sky.config.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "sys_dict_data")
public class DictType extends BaseEntity {
    private String dictName;
    private String dictType;
    private String remark;
}
