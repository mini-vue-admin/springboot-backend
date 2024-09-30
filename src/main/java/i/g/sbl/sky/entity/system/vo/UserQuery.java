package i.g.sbl.sky.entity.system.vo;

import i.g.sbl.sky.entity.system.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserQuery extends User {

    /**
     * 角色ID，表示查询所有属于该角色ID的用户
     */
    private String roleId;

    /**
     * 反向角色查询，表示查询所有不属于该角色ID的用户
     */
    private Boolean roleReverse;

    /**
     * 关键字查询，基于用户名、用户昵称、邮箱、手机号多个字段模糊查询
     */
    private String keyword;
}
