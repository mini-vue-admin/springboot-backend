package ${MODULE_PACKAGE};

import i.g.sbl.sky.basic.exception.NotFoundException;
import i.g.sbl.sky.basic.model.PageData;
import ${BASE_PACKAGE}.entity.${MODULE_NAME}.${ENTITY_NAME};
import ${BASE_PACKAGE}.repo.${MODULE_NAME}.${ENTITY_NAME}Repo;
import ${BASE_PACKAGE}.service.${MODULE_NAME}.${ENTITY_NAME}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ${ENTITY_NAME}ServiceImpl implements ${ENTITY_NAME}Service {
    @Autowired
    private ${ENTITY_NAME}Repo ${ENTITY_FIELD_NAME}Repo;


    @Override
    public Optional<${ENTITY_NAME}> findById(Long id) {
        return ${ENTITY_FIELD_NAME}Repo.findById(id);
    }

    @Override
    public List<${ENTITY_NAME}> findAll(${ENTITY_NAME} query) {
        return ${ENTITY_FIELD_NAME}Repo.findByFilter(query);
    }

    @Override
    public PageData<${ENTITY_NAME}> findAll(${ENTITY_NAME} query, PageData<${ENTITY_NAME}> pageable) {
        return ${ENTITY_FIELD_NAME}Repo.findByFilter(query, pageable);
    }

    @Transactional
    @Override
    public ${ENTITY_NAME} create(${ENTITY_NAME} ${ENTITY_FIELD_NAME}) {
        return ${ENTITY_FIELD_NAME}Repo.save(${ENTITY_FIELD_NAME});
    }

    @Transactional
    @Override
    public ${ENTITY_NAME} update(${ENTITY_NAME} ${ENTITY_FIELD_NAME}) {
        ${ENTITY_NAME} item = ${ENTITY_FIELD_NAME}Repo.findById(${ENTITY_FIELD_NAME}.getId()).orElseThrow(NotFoundException::new);
        item.copyNonNulls(${ENTITY_FIELD_NAME});
        return ${ENTITY_FIELD_NAME}Repo.save(item);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        ${ENTITY_FIELD_NAME}Repo.deleteById(id);
    }

    @Transactional
    @Override
    public void delete(List<Long> id) {
        ${ENTITY_FIELD_NAME}Repo.deleteAllById(id);
    }
}
