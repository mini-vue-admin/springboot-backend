package i.g.sbl.sky.repo.system;

import i.g.sbl.sky.basic.jpa.Filter;
import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LogRepo extends CrudRepository<Log, Long>, JpaSpecificationExecutor<Log> {
    
    default List<Log> findByFilter(Log query) {
        return this.findAll(
                Filter.of(query)
        );
    }

    default PageData<Log> findByFilter(Log query, PageData<Log> pageable) {
        Page<Log> page = this.findAll(
                Filter.of(query),
                pageable.toPageRequest()
        );
        return PageData.of(page);
    }
}
