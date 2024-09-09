package i.g.sbl.sky.entity.team;

import i.g.sbl.sky.basic.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "sky_team")
public class Team extends BaseEntity {
    private String name;
    private String remark;


}
