package i.g.sbl.sky.repo.system;

import i.g.sbl.sky.basic.jpa.filters.Filter;
import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.DictData;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DictDataRepo extends JpaRepository<DictData, String>, JpaSpecificationExecutor<DictData> {
    
    default List<DictData> findByFilter(DictData query) {
        return this.findAll(
                Filter.of(query)
        );
    }

    default PageData<DictData> findByFilter(DictData query, PageData<DictData> pageable) {
        Page<DictData> page = this.findAll(
                Filter.of(query),
                pageable.toPageRequest()
        );
        return PageData.of(page);
    }
}
