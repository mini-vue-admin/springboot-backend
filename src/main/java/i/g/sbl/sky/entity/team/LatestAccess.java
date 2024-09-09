package i.g.sbl.sky.entity.team;


import i.g.sbl.sky.basic.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "sky_latest_access")
public class LatestAccess extends BaseEntity {
    private Long userId;
    private Long resourceId;
    private String resourceType;
}
