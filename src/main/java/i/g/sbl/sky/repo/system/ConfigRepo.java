package i.g.sbl.sky.repo.system;

import i.g.sbl.sky.basic.jpa.Filter;
import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.Config;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ConfigRepo extends CrudRepository<Config, Long>, JpaSpecificationExecutor<Config> {

    default List<Config> findByFilter(Config query) {
        return this.findAll(
                Filter.of(query)
                        .like(Config::getConfigKey)
                        .like(Config::getConfigName)
                        .eq(Config::getConfigType)
        );
    }

    default PageData<Config> findByFilter(Config query, PageData<Config> pageable) {
        Page<Config> page = this.findAll(
                Filter.of(query)
                        .like(Config::getConfigKey)
                        .like(Config::getConfigName)
                        .eq(Config::getConfigType),
                pageable.toPageRequest()
        );
        return PageData.of(page);
    }
}
