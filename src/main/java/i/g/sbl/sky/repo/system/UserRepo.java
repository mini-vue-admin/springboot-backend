package i.g.sbl.sky.repo.system;

import com.querydsl.jpa.impl.JPAQuery;
import i.g.sbl.sky.basic.jpa.dsl.QueryDslRepository;
import i.g.sbl.sky.basic.jpa.filters.Filter;
import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.User;
import i.g.sbl.sky.entity.system.vo.UserQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

import static i.g.sbl.sky.entity.system.QRoleUser.roleUser;
import static i.g.sbl.sky.entity.system.QUser.user;

public interface UserRepo extends JpaRepository<User, String>, JpaSpecificationExecutor<User>, QuerydslPredicateExecutor<User>, QueryDslRepository<User> {

    default Specification<User> buildFilter(UserQuery query) {
        return Filter.of(User.class)
                .like(User::getUsername, query.getUsername())
                .like(User::getNickname, query.getNickname())
                .like(User::getEmail, query.getEmail())
                .like(User::getPhone, query.getPhone())
                .eq(User::getStatus, query.getStatus())
                .and(
                        Filter.of(User.class)
                                .or(
                                        Filter.of(User.class).like(User::getNickname, query.getKeyword()),
                                        Filter.of(User.class).like(User::getUsername, query.getKeyword()),
                                        Filter.of(User.class).like(User::getEmail, query.getKeyword()),
                                        Filter.of(User.class).like(User::getPhone, query.getKeyword())
                                )
                );
    }

    default List<User> findByFilter(UserQuery query) {
        return this.findAll(
                buildFilter(query)
        );
    }

    default PageData<User> findByFilter(UserQuery query, PageData<User> pageable) {
        Page<User> page = this.findAll(
                buildFilter(query),
                pageable.toPageRequest()
        );
        return PageData.of(page);
    }

    Optional<User> findByUsername(String username);


    default PageData<User> findRoleMembers(UserQuery query, PageData<User> pageable) {
        Page<User> page = findAll(
                new JPAQuery<>()
                        .select(user)
                        .from(user)
                        .innerJoin(roleUser)
                        .on(user.id.eq(roleUser.userId))
                        .where(roleUser.roleId.eq(query.getRoleId())),
                pageable.toPageRequest()
        );
        return PageData.of(page);
    }
}
