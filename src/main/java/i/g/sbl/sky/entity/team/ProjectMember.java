package i.g.sbl.sky.entity.team;

import i.g.sbl.sky.basic.cons.team.MemberRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "sky_project_member")
public class ProjectMember {
    @Id
    private Long projectId;
    @Id
    private Long userId;

    private MemberRole role;

    @CreationTimestamp
    @Column(name = "create_time", updatable = false)
    private LocalDateTime createTime;
}
