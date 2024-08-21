package ${MODULE_PACKAGE};

import i.g.sbl.sky.basic.model.PageData;
import ${BASE_PACKAGE}.entity.${MODULE_NAME}.${ENTITY_NAME};

import java.util.List;
import java.util.Optional;

public interface ${ENTITY_NAME}Service {

    Optional<${ENTITY_NAME}> findById(<#if ENTITY_PRIMARY_KEYS?? && (ENTITY_PRIMARY_KEYS?size gt 1)>${ENTITY_NAME}.${ENTITY_NAME}Id<#else>Long</#if> id);

    List<${ENTITY_NAME}> findAll(${ENTITY_NAME} query);

    PageData<${ENTITY_NAME}> findAll(${ENTITY_NAME} query, PageData<${ENTITY_NAME}> pageable);

    ${ENTITY_NAME} create(${ENTITY_NAME} ${ENTITY_FIELD_NAME});

<#if !(ENTITY_PRIMARY_KEYS?? && (ENTITY_PRIMARY_KEYS?size gt 1))>
    ${ENTITY_NAME} update(${ENTITY_NAME} ${ENTITY_FIELD_NAME});
</#if>

    void delete(<#if ENTITY_PRIMARY_KEYS?? && (ENTITY_PRIMARY_KEYS?size gt 1)>${ENTITY_NAME}.${ENTITY_NAME}Id<#else>Long</#if> id);

    void delete(List<<#if ENTITY_PRIMARY_KEYS?? && (ENTITY_PRIMARY_KEYS?size gt 1)>${ENTITY_NAME}.${ENTITY_NAME}Id<#else>Long</#if>> id);

}
