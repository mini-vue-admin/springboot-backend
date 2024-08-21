package i.g.sbl.sky.service.system;

import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.RoleMenu;

import java.util.List;
import java.util.Optional;

public interface RoleMenuService {

    Optional<RoleMenu> findById(RoleMenu.RoleMenuId id);

    List<RoleMenu> findAll(RoleMenu query);

    PageData<RoleMenu> findAll(RoleMenu query, PageData<RoleMenu> pageable);

    RoleMenu create(RoleMenu roleMenu);


    void delete(RoleMenu.RoleMenuId id);

    void delete(List<RoleMenu.RoleMenuId> id);

}
