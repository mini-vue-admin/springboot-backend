package i.g.sbl.sky.repo.system;

import i.g.sbl.sky.basic.jpa.Filter;
import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.RoleMenu;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoleMenuRepo extends CrudRepository<RoleMenu, RoleMenu.RoleMenuId>, JpaSpecificationExecutor<RoleMenu> {

    default List<RoleMenu> findByFilter(RoleMenu query) {
        return this.findAll(
                Filter.of(query)
        );
    }

    default PageData<RoleMenu> findByFilter(RoleMenu query, PageData<RoleMenu> pageable) {
        Page<RoleMenu> page = this.findAll(
                Filter.of(query),
                pageable.toPageRequest()
        );
        return PageData.of(page);
    }
}
