package i.g.sbl.sky.repo.system;

import i.g.sbl.sky.basic.jpa.JpaFilter;
import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.Config;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ConfigRepo extends CrudRepository<Config, Long>, JpaFilter<Config> {

}
