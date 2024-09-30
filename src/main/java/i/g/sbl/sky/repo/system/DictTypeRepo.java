package i.g.sbl.sky.repo.system;

import i.g.sbl.sky.basic.jpa.filters.Filter;
import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.DictType;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DictTypeRepo extends CrudRepository<DictType, String>, JpaSpecificationExecutor<DictType> {
    
    default List<DictType> findByFilter(DictType query) {
        return this.findAll(
                Filter.of(query)
        );
    }

    default PageData<DictType> findByFilter(DictType query, PageData<DictType> pageable) {
        Page<DictType> page = this.findAll(
                Filter.of(query),
                pageable.toPageRequest()
        );
        return PageData.of(page);
    }
}
