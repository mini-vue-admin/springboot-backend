package i.g.sbl.sky.service.system;

import i.g.sbl.sky.basic.model.DetailedUser;
import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findById(String id);

    Optional<User> getByUsername(String username);

    Optional<DetailedUser> getDetailedUserByUsername(String username);

    List<User> findAll(User query);

    PageData<User> findAll(User query, PageData<User> pageable);

    User create(User user);

    User update(User user);

    void delete(String id);

    void delete(List<String> id);

    void validate(String username, String password);
}
