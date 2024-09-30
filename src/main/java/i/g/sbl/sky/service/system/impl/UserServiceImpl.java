package i.g.sbl.sky.service.system.impl;

import i.g.sbl.sky.basic.exception.AuthenticationException;
import i.g.sbl.sky.basic.exception.NotFoundException;
import i.g.sbl.sky.basic.model.DetailedUser;
import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.User;
import i.g.sbl.sky.repo.system.UserRepo;
import i.g.sbl.sky.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;


    @Override
    public Optional<User> findById(String id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public Optional<DetailedUser> getDetailedUserByUsername(String username) {
        Optional<User> user = getByUsername(username);
        return user.map(DetailedUser::new);
    }

    @Override
    public List<User> findAll(User query) {
        return userRepo.findByFilter(query);
    }

    @Override
    public PageData<User> findAll(User query, PageData<User> pageable) {
        return userRepo.findByFilter(query, pageable);
    }

    @Transactional
    @Override
    public User create(User user) {
        return userRepo.save(user);
    }

    @Transactional
    @Override
    public User update(User user) {
        User item = userRepo.findById(user.getId()).orElseThrow(NotFoundException::new);
        item.copyNonNulls(user);
        return userRepo.save(item);
    }

    @Transactional
    @Override
    public void delete(String id) {
        userRepo.deleteById(id);
    }

    @Transactional
    @Override
    public void delete(List<String> id) {
        userRepo.deleteAllById(id);
    }

    @Override
    public void validate(String username, String password) {
        Optional<User> user = userRepo.findByUsernameAndPassword(username, password);
        if (user.isEmpty()) {
            throw new AuthenticationException("Invalid username or password");
        }
    }
}
