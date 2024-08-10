package i.g.sbl.sky.service.system;

import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.Log;

import java.util.List;
import java.util.Optional;

public interface LogService {

    Optional<Log> findById(Long id);

    List<Log> findAll(Log query);

    PageData<Log> findAll(Log query, PageData<Log> pageable);

    Log create(Log log);

    Log update(Log log);

    void delete(Long id);

    void delete(List<Long> id);

}
