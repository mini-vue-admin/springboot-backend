package ${MODULE_PACKAGE};

import i.g.sbl.sky.basic.model.PageData;
import ${BASE_PACKAGE}.entity.${MODULE_NAME}.${ENTITY_NAME};

import java.util.List;
import java.util.Optional;

public interface ${ENTITY_NAME}Service {

    Optional<${ENTITY_NAME}> findById(Long id);

    List<${ENTITY_NAME}> findAll(${ENTITY_NAME} query);

    PageData<${ENTITY_NAME}> findAll(${ENTITY_NAME} query, PageData<${ENTITY_NAME}> pageable);

    ${ENTITY_NAME} create(${ENTITY_NAME} ${ENTITY_FIELD_NAME});

    ${ENTITY_NAME} update(${ENTITY_NAME} ${ENTITY_FIELD_NAME});

    void delete(Long id);

    void delete(List<Long> id);

}
