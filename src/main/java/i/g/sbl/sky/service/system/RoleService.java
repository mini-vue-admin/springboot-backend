package i.g.sbl.sky.service.system;

import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    Optional<Role> findById(String id);

    List<Role> findAll(Role query);

    PageData<Role> findAll(Role query, PageData<Role> pageable);

    Role create(Role role);

    Role update(Role role);

    void delete(String id);

    void delete(List<String> id);

}
