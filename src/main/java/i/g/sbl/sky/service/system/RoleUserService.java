package i.g.sbl.sky.service.system;

import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.RoleUser;

import java.util.List;

public interface RoleUserService {

    List<RoleUser> findAll(RoleUser query);

    PageData<RoleUser> findAll(RoleUser query, PageData<RoleUser> pageable);

    RoleUser create(RoleUser roleUser);

}
