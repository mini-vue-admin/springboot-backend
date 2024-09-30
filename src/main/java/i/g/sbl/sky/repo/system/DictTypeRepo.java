package i.g.sbl.sky.repo.system;

import i.g.sbl.sky.basic.jpa.filters.Filter;
import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.DictType;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface DictTypeRepo extends JpaRepository<DictType, String>, JpaSpecificationExecutor<DictType> {

    default List<DictType> findByFilter(DictType query) {
        return this.findAll(
                Filter.of(query)
                        .like(DictType::getDictName)
                        .like(DictType::getDictType)
        );
    }

    default PageData<DictType> findByFilter(DictType query, PageData<DictType> pageable) {
        Page<DictType> page = this.findAll(
                Filter.of(query)
                        .like(DictType::getDictName)
                        .like(DictType::getDictType),
                pageable.toPageRequest()
        );
        return PageData.of(page);
    }

    Optional<DictType> findByDictType(String dictType);
}
