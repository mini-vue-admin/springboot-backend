package i.g.sbl.sky.repo.system;

import i.g.sbl.sky.entity.system.Role;
import i.g.sbl.sky.entity.system.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoleRepo extends CrudRepository<Role, Long> {
}
