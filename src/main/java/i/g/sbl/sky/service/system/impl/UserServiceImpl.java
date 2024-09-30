package i.g.sbl.sky.service.system.impl;

import i.g.sbl.sky.basic.cons.ConfigKeys;
import i.g.sbl.sky.basic.exception.AuthenticationException;
import i.g.sbl.sky.basic.exception.BusinessException;
import i.g.sbl.sky.basic.exception.NotFoundException;
import i.g.sbl.sky.basic.model.DetailedUser;
import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.Role;
import i.g.sbl.sky.entity.system.User;
import i.g.sbl.sky.entity.system.UserPassword;
import i.g.sbl.sky.entity.system.vo.UserQuery;
import i.g.sbl.sky.repo.system.UserPasswordRepo;
import i.g.sbl.sky.repo.system.UserRepo;
import i.g.sbl.sky.service.system.ConfigService;
import i.g.sbl.sky.service.system.RoleService;
import i.g.sbl.sky.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ConfigService configService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserPasswordRepo userPasswordRepo;


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
        return user.map(it -> {
            DetailedUser detailedUser = new DetailedUser(it);
            List<String> roles = roleService.findByUserId(it.getId()).stream().map(Role::getRoleKey).toList();
            detailedUser.setRoles(roles);
            return detailedUser;
        });
    }

    @Override
    public List<User> findAll(UserQuery query) {
        return userRepo.findByFilter(query);
    }

    @Override
    public PageData<User> findAll(UserQuery query, PageData<User> pageable) {
        return userRepo.findByFilter(query, pageable);
    }

    private void validate(User user) {
        userRepo.findByUsername(user.getUsername()).ifPresent(exist -> {
            if (user.getId() == null || !Objects.equals(exist.getId(), user.getId())) {
                throw new BusinessException("用户名已存在");
            }
        });
    }

    @Transactional
    @Override
    public User create(User user) {
        validate(user);

        // 注意此处需要使用saveAndFlush
        User created = userRepo.saveAndFlush(user);
        this.resetPassword(created.getId());
        return created;
    }

    @Transactional
    @Override
    public User update(User user) {
        validate(user);
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
        userRepo.deleteAllByIdInBatch(id);
    }

    @Override
    public void validatePassword(String username, String password) throws AuthenticationException {
        Optional<UserPassword> user = userPasswordRepo.findByUsernameAndPassword(username, password);
        if (user.isEmpty()) {
            throw new AuthenticationException("Authentication failed, invalid username or password");
        }
    }

    @Transactional
    @Override
    public void resetPassword(String id) {
        UserPassword user = userPasswordRepo.findById(id).orElseThrow(NotFoundException::new);
        user.setPassword(configService.getValue(ConfigKeys.USER_DEFAULT_PASSWORD, "88888888"));
        userPasswordRepo.save(user);
    }

}
