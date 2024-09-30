package i.g.sbl.sky.service.system;

import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.DictType;

import java.util.List;
import java.util.Optional;

public interface DictTypeService {

    Optional<DictType> findById(String id);

    List<DictType> findAll(DictType query);

    PageData<DictType> findAll(DictType query, PageData<DictType> pageable);

    DictType create(DictType dictType);

    DictType update(DictType dictType);

    void delete(String id);

    void delete(List<String> id);

}
