package i.g.sbl.sky.repo.system;

import i.g.sbl.sky.entity.system.UserPassword;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserPasswordRepo extends CrudRepository<UserPassword, String> {

    Optional<UserPassword> findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
