package i.g.sbl.sky.repo.system;

import i.g.sbl.sky.basic.jpa.filters.Filter;
import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface LogRepo extends JpaRepository<Log, String>, JpaSpecificationExecutor<Log> {

    default List<Log> findByFilter(Log query) {
        return this.findAll(
                Filter.of(query)
                        .like(Log::getMsg)
                        .eq(Log::getLevel)
                        .like(Log::getUsername)
                        .like(Log::getNickname)
                        .eq(Log::getType)
        );
    }

    default PageData<Log> findByFilter(Log query, PageData<Log> pageable) {
        Page<Log> page = this.findAll(
                Filter.of(query)
                        .like(Log::getMsg)
                        .eq(Log::getLevel)
                        .like(Log::getUsername)
                        .like(Log::getNickname)
                        .eq(Log::getType),
                pageable.toPageRequest()
        );
        return PageData.of(page);
    }
}
