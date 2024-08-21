package ${MODULE_PACKAGE};

import i.g.sbl.sky.basic.jpa.Filter;
import i.g.sbl.sky.basic.model.PageData;
import ${BASE_PACKAGE}.entity.${MODULE_NAME}.${ENTITY_NAME};
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ${ENTITY_NAME}Repo extends CrudRepository<${ENTITY_NAME}, <#if ENTITY_PRIMARY_KEYS?? && (ENTITY_PRIMARY_KEYS?size gt 1)>${ENTITY_NAME}.${ENTITY_NAME}Id<#else>Long</#if>>, JpaSpecificationExecutor<${ENTITY_NAME}> {
    
    default List<${ENTITY_NAME}> findByFilter(${ENTITY_NAME} query) {
        return this.findAll(
                Filter.of(query)
        );
    }

    default PageData<${ENTITY_NAME}> findByFilter(${ENTITY_NAME} query, PageData<${ENTITY_NAME}> pageable) {
        Page<${ENTITY_NAME}> page = this.findAll(
                Filter.of(query),
                pageable.toPageRequest()
        );
        return PageData.of(page);
    }
}
