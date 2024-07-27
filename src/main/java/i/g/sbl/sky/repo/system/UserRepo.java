package i.g.sbl.sky.repo.system;

import i.g.sbl.sky.entity.system.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepo extends CrudRepository<User, Long> {

    List<User> findAll();

    User save(User user);

    User findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);
}
