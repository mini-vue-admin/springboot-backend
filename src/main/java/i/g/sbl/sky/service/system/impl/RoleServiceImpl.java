package i.g.sbl.sky.service.system.impl;

import i.g.sbl.sky.basic.exception.BusinessException;
import i.g.sbl.sky.basic.exception.NotFoundException;
import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.*;
import i.g.sbl.sky.entity.system.vo.MemberIds;
import i.g.sbl.sky.entity.system.vo.MenuQuery;
import i.g.sbl.sky.entity.system.vo.UserQuery;
import i.g.sbl.sky.repo.system.*;
import i.g.sbl.sky.service.system.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleUserRepo roleUserRepo;
    @Autowired
    private MenuRepo menuRepo;
    @Autowired
    private RoleMenuRepo roleMenuRepo;


    @Override
    public Optional<Role> findById(String id) {
        return roleRepo.findById(id);
    }

    @Override
    public List<Role> findAll(Role query) {
        return roleRepo.findByFilter(query);
    }

    @Override
    public PageData<Role> findAll(Role query, PageData<Role> pageable) {
        return roleRepo.findByFilter(query, pageable);
    }

    private void validate(Role role) {
        roleRepo.findByRoleKey(role.getRoleKey()).ifPresent(exist -> {
            if (role.getId() == null || !Objects.equals(exist.getId(), role.getId())) {
                throw new BusinessException("角色已存在");
            }
        });
    }

    @Transactional
    @Override
    public Role create(Role role) {
        return roleRepo.save(role);
    }

    @Transactional
    @Override
    public Role update(Role role) {
        Role item = roleRepo.findById(role.getId()).orElseThrow(NotFoundException::new);
        item.copyNonNulls(role);
        return roleRepo.save(item);
    }

    @Transactional
    @Override
    public void delete(String id) {
        roleRepo.deleteById(id);
    }

    @Transactional
    @Override
    public void delete(List<String> id) {
        roleRepo.deleteAllByIdInBatch(id);
    }

    @Override
    public List<Role> findByUserId(String userId) {
        return roleRepo.findByUserId(userId);
    }

    @Override
    public PageData<User> findMemberPage(UserQuery query, PageData<User> page) {
        return userRepo.findRoleMembers(query, page);
    }

    @Transactional
    @Override
    public void addMembers(String roleId, MemberIds userIds) {
        List<String> members = userIds.getMemberIds();
        for (String member : members) {
            roleUserRepo.save(new RoleUser(member, roleId));
        }
    }

    @Transactional
    @Override
    public void deleteMember(String roleId, MemberIds userIds) {
        List<String> members = userIds.getMemberIds();
        for (String member : members) {
            roleUserRepo.deleteById(new RoleUser.RoleUserId(member, roleId));
        }
    }

    @Override
    public PageData<Menu> findMenuPage(MenuQuery query, PageData<User> pageable) {
        PageData<Menu> page = menuRepo.findRoleMenus(query, pageable);
        if (query.getChildRecursion() != null && query.getChildRecursion()) {
            for (Menu menu : page.getList()) {
                List<Menu> children = menuRepo.findByParentId(menu.getId());
                menu.setChildren(children);
            }
        }
        return page;
    }

    @Transactional
    @Override
    public void addMenu(String roleId, MemberIds menuIds) {
        List<String> members = menuIds.getMemberIds();
        for (String member : members) {
            roleMenuRepo.save(new RoleMenu(member, roleId));
        }
    }

    @Transactional
    @Override
    public void deleteMenu(String roleId, MemberIds menuIds) {
        List<String> members = menuIds.getMemberIds();
        for (String member : members) {
            roleMenuRepo.deleteById(new RoleMenu.RoleMenuId(member, roleId));
        }
    }
}
