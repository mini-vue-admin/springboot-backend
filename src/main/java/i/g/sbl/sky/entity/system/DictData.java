package i.g.sbl.sky.entity.system;

import i.g.sbl.sky.basic.cons.Status;
import i.g.sbl.sky.config.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;


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
}
