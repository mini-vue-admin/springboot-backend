package i.g.sbl.sky.service.system;

import i.g.sbl.sky.basic.exception.AuthenticationException;
import i.g.sbl.sky.basic.model.DetailedUser;
import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.User;
import i.g.sbl.sky.entity.system.vo.UserQuery;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findById(String id);

    Optional<User> getByUsername(String username);

    Optional<DetailedUser> getDetailedUserByUsername(String username);

    List<User> findAll(UserQuery query);

    PageData<User> findAll(UserQuery query, PageData<User> pageable);

    User create(User user);

    User update(User user);

    void delete(String id);

    void delete(List<String> id);

    void validatePassword(String username, String password) throws AuthenticationException;

    void resetPassword(String id);
}
