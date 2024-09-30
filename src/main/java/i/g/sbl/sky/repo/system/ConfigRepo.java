package i.g.sbl.sky.repo.system;

import i.g.sbl.sky.basic.jpa.filters.Filter;
import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.Config;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ConfigRepo extends JpaRepository<Config, String>, JpaSpecificationExecutor<Config> {

    default List<Config> findByFilter(Config query) {
        return this.findAll(
                Filter.of(query)
                        .like(Config::getConfigKey)
                        .like(Config::getConfigName)
                        .like(Config::getConfigValue)
                        .eq(Config::getConfigType)
        );
    }

    default PageData<Config> findByFilter(Config query, PageData<Config> pageable) {
        Page<Config> page = this.findAll(
                Filter.of(query)
                        .like(Config::getConfigKey)
                        .like(Config::getConfigName)
                        .like(Config::getConfigValue)
                        .eq(Config::getConfigType),
                pageable.toPageRequest()
        );
        return PageData.of(page);
    }

    Optional<Config> findByConfigKey(String key);
}
