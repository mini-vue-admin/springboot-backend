package i.g.sbl.sky.repo.system;

import i.g.sbl.sky.basic.jpa.filters.Filter;
import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MenuRepo extends CrudRepository<Menu, String>, JpaSpecificationExecutor<Menu> {
    
    default List<Menu> findByFilter(Menu query) {
        return this.findAll(
                Filter.of(query)
        );
    }

    default PageData<Menu> findByFilter(Menu query, PageData<Menu> pageable) {
        Page<Menu> page = this.findAll(
                Filter.of(query),
                pageable.toPageRequest()
        );
        return PageData.of(page);
    }
}
