package i.g.sbl.sky.entity.system.vo;

import i.g.sbl.sky.entity.system.Menu;
import lombok.Data;

@Data
public class MenuQuery extends Menu {
    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 是否递归返回所有子节点
     */
    private Boolean childRecursion;
}
