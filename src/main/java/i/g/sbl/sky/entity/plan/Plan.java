package i.g.sbl.sky.entity.plan;

import i.g.sbl.sky.basic.cons.PlanStatus;
import i.g.sbl.sky.basic.cons.PlanType;
import i.g.sbl.sky.config.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "sky_plan")
public class Plan extends BaseEntity {
    private Long projectId;
    private String name;
    private String remark;
    private PlanType type;
    private PlanStatus status;
}
