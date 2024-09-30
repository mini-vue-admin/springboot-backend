package i.g.sbl.sky.service.system;

import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.DictData;

import java.util.List;
import java.util.Optional;

public interface DictDataService {

    Optional<DictData> findById(String id);

    List<DictData> findAll(DictData query);

    PageData<DictData> findAll(DictData query, PageData<DictData> pageable);

    DictData create(DictData dictData);

    DictData update(DictData dictData);

    void delete(String id);

    void delete(List<String> id);

}
