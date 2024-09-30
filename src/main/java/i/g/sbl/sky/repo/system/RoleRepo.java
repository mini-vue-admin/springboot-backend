package i.g.sbl.sky.repo.system;

import i.g.sbl.sky.basic.jpa.filters.Filter;
import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoleRepo extends CrudRepository<Role, String>, JpaSpecificationExecutor<Role> {
    
    default List<Role> findByFilter(Role query) {
        return this.findAll(
                Filter.of(query)
        );
    }

    default PageData<Role> findByFilter(Role query, PageData<Role> pageable) {
        Page<Role> page = this.findAll(
                Filter.of(query),
                pageable.toPageRequest()
        );
        return PageData.of(page);
    }
}
