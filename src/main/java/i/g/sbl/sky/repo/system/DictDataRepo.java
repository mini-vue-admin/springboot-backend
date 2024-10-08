package i.g.sbl.sky.repo.system;

import i.g.sbl.sky.basic.jpa.filters.Filter;
import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.DictData;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface DictDataRepo extends JpaRepository<DictData, String>, JpaSpecificationExecutor<DictData> {

    default List<DictData> findByFilter(DictData query) {
        return this.findAll(
                Filter.of(query)
                        .like(DictData::getDictLabel)
                        .eq(DictData::getDictValue)
                        .eq(DictData::getDictType)
        );
    }

    default PageData<DictData> findByFilter(DictData query, PageData<DictData> pageable) {
        Page<DictData> page = this.findAll(
                Filter.of(query)
                        .like(DictData::getDictLabel)
                        .eq(DictData::getDictValue)
                        .eq(DictData::getDictType)
                ,
                pageable.toPageRequest()
        );
        return PageData.of(page);
    }

    Optional<DictData> findByDictTypeAndDictValue(String dictType, String dictValue);
}
