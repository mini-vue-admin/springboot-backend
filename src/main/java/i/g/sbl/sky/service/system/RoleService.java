package i.g.sbl.sky.service.system;

import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    Optional<Role> findById(Long id);

    List<Role> findAll(Role query);

    PageData<Role> findAll(Role query, PageData<Role> pageable);

    Role create(Role role);

    Role update(Role role);

    void delete(Long id);

    void delete(List<Long> id);

}
