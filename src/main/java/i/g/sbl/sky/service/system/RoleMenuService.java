package i.g.sbl.sky.service.system;

import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.RoleMenu;

import java.util.List;

public interface RoleMenuService {

    List<RoleMenu> findAll(RoleMenu query);

    PageData<RoleMenu> findAll(RoleMenu query, PageData<RoleMenu> pageable);

    RoleMenu create(RoleMenu roleMenu);
}
