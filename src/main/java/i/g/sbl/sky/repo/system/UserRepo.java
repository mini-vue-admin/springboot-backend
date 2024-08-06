package i.g.sbl.sky.repo.system;

import i.g.sbl.sky.basic.jpa.Filter;
import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepo extends CrudRepository<User, Long>, JpaSpecificationExecutor<User> {
    
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

    User findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);
}
