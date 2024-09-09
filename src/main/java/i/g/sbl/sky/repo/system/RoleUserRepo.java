package i.g.sbl.sky.repo.system;

import i.g.sbl.sky.basic.jpa.filters.Filter;
import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.RoleUser;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoleUserRepo extends CrudRepository<RoleUser, RoleUser.RoleUserId>, JpaSpecificationExecutor<RoleUser> {
    
    default List<RoleUser> findByFilter(RoleUser query) {
        return this.findAll(
                Filter.of(query)
        );
    }

    default PageData<RoleUser> findByFilter(RoleUser query, PageData<RoleUser> pageable) {
        Page<RoleUser> page = this.findAll(
                Filter.of(query),
                pageable.toPageRequest()
        );
        return PageData.of(page);
    }
}
