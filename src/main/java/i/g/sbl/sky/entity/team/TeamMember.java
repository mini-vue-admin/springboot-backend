package i.g.sbl.sky.entity.team;

import i.g.sbl.sky.basic.cons.MemberRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "sky_team_member")
public class TeamMember {
    @Id
    private Long teamId;
    @Id
    private Long userId;

    private MemberRole role;

    @CreationTimestamp
    @Column(name = "create_time", updatable = false)
    private LocalDateTime createTime;
}
