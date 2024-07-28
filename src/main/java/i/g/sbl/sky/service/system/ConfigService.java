package i.g.sbl.sky.service.system;

import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.Config;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ConfigService {

    Optional<Config> findById(Long id);

    List<Config> findAll(Config query);

    PageData<Config> findAll(Config query, Pageable pageable);

    Config create(Config config);

    Config update(Config config);

    void delete(Long id);

    void delete(List<Long> id);

}
