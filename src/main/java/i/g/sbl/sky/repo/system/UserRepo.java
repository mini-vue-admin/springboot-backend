package i.g.sbl.sky.repo.system;

import i.g.sbl.sky.basic.jpa.dsl.QueryDslRepository;
import i.g.sbl.sky.basic.jpa.filters.Filter;
import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.User;
import org.hibernate.annotations.SQLSelect;
import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, String>, JpaSpecificationExecutor<User>, QuerydslPredicateExecutor<User>, QueryDslRepository<User> {

    default List<User> findByFilter(User query) {
        return this.findAll(
                Filter.of(query)
        );
    }

    default PageData<User> findByFilter(User query, PageData<User> pageable) {
        Page<User> page = this.findAll(
                Filter.of(query),
                pageable.toPageRequest()
        );
        return PageData.of(page);
    }

    Optional<User> findByUsername(String username);


}
