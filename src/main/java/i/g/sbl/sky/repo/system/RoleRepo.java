package i.g.sbl.sky.repo.system;

import com.querydsl.jpa.impl.JPAQuery;
import i.g.sbl.sky.basic.jpa.dsl.QueryDslRepository;
import i.g.sbl.sky.basic.jpa.filters.Filter;
import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.Role;
import i.g.sbl.sky.entity.system.User;
import i.g.sbl.sky.entity.system.vo.UserQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

import static i.g.sbl.sky.entity.system.QRole.role;
import static i.g.sbl.sky.entity.system.QRoleUser.roleUser;
import static i.g.sbl.sky.entity.system.QUser.user;

public interface RoleRepo extends JpaRepository<Role, String>, JpaSpecificationExecutor<Role>, QueryDslRepository<Role> {

    default List<Role> findByFilter(Role query) {
        return this.findAll(
                Filter.of(query)
                        .like(Role::getRoleName)
                        .eq(Role::getRoleKey)
        );
    }

    default PageData<Role> findByFilter(Role query, PageData<Role> pageable) {
        Page<Role> page = this.findAll(
                Filter.of(query)
                        .like(Role::getRoleName)
                        .eq(Role::getRoleKey),
                pageable.toPageRequest()
        );
        return PageData.of(page);
    }

    Optional<Role> findByRoleKey(String roleKey);

    default List<Role> findByUserId(String userId) {
        return findAll(new JPAQuery<>()
                .select(role)
                .from(role)
                .innerJoin(roleUser)
                .on(role.id.eq(roleUser.roleId))
                .where(roleUser.userId.eq(userId))
        );
    }

}
