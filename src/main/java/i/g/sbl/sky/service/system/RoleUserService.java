package i.g.sbl.sky.service.system;

import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.RoleUser;

import java.util.List;
import java.util.Optional;

public interface RoleUserService {

    Optional<RoleUser> findById(RoleUser.RoleUserId id);

    List<RoleUser> findAll(RoleUser query);

    PageData<RoleUser> findAll(RoleUser query, PageData<RoleUser> pageable);

    RoleUser create(RoleUser roleUser);


    void delete(RoleUser.RoleUserId id);

    void delete(List<RoleUser.RoleUserId> id);

}
