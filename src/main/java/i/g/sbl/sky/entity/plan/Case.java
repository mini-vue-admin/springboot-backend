package i.g.sbl.sky.entity.plan;

import i.g.sbl.sky.basic.cons.PlanStatus;
import i.g.sbl.sky.config.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "sky_case")
public class Case extends BaseEntity {
    private Long planId;
    private Long creatorId;
    private String name;
    private String remark;
    private PlanStatus status;
}
