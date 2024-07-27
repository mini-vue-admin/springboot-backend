package i.g.sbl.sky.service.system;

import i.g.sbl.sky.basic.exception.AuthenticationException;
import i.g.sbl.sky.basic.model.DetailedUser;
import i.g.sbl.sky.entity.system.User;
import i.g.sbl.sky.repo.system.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public User getByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public DetailedUser getDetailedUserByUsername(String username) {
        DetailedUser detailedUser = new DetailedUser(getByUsername(username));

        return detailedUser;
    }

    public void validate(String username, String password) {
        User user = userRepo.findByUsernameAndPassword(username, password);
        if (user == null) {
            throw new AuthenticationException("Invalid username or password");
        }
    }
}
