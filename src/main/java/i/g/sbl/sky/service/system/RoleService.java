package i.g.sbl.sky.service.system;

import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.Menu;
import i.g.sbl.sky.entity.system.Role;
import i.g.sbl.sky.entity.system.User;
import i.g.sbl.sky.entity.system.vo.MemberIds;
import i.g.sbl.sky.entity.system.vo.MenuQuery;
import i.g.sbl.sky.entity.system.vo.UserQuery;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    Optional<Role> findById(String id);

    List<Role> findAll(Role query);

    PageData<Role> findAll(Role query, PageData<Role> pageable);

    Role create(Role role);

    Role update(Role role);

    void delete(String id);

    void delete(List<String> id);

    List<Role> findByUserId(String userId);

    PageData<User> findMemberPage(UserQuery query, PageData<User> page);

    @Transactional
    void addMembers(String roleId, MemberIds memberIds);

    @Transactional
    void deleteMember(String roleId, MemberIds userIds);

    PageData<Menu> findMenuPage(MenuQuery query, PageData<User> page);

    @Transactional
    void addMenu(String roleId, MemberIds menuIds);

    @Transactional
    void deleteMenu(String roleId, MemberIds menuIds);
}
