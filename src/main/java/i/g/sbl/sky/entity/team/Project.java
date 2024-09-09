package i.g.sbl.sky.entity.team;

import i.g.sbl.sky.basic.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "sky_project")
public class Project extends BaseEntity {
    private Long teamId;
    private Long creatorId;
    private String name;
    private String remark;

}
