package i.g.sbl.sky.entity.team;


import i.g.sbl.sky.basic.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "sky_favorite_access")
public class FavoriteAccess extends BaseEntity {
    private Long userId;
    private Long resourceId;
    private String resourceType;
}
