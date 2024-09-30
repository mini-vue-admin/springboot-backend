package i.g.sbl.sky.entity.system.vo;

import i.g.sbl.sky.entity.system.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserQuery extends User {

    private String roleId;

    private String keyword;
}
