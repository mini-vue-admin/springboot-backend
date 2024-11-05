package i.g.sbl.sky.service.system.impl;

import i.g.sbl.sky.basic.exception.BusinessException;
import i.g.sbl.sky.basic.exception.NotFoundException;
import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.DictData;
import i.g.sbl.sky.entity.system.DictType;
import i.g.sbl.sky.repo.system.DictTypeRepo;
import i.g.sbl.sky.service.system.DictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DictTypeServiceImpl implements DictTypeService {
    @Autowired
    private DictTypeRepo dictTypeRepo;


    @Override
    public Optional<DictType> findById(String id) {
        return dictTypeRepo.findById(id);
    }

    @Override
    public List<DictType> findAll(DictType query) {
        return dictTypeRepo.findByFilter(query);
    }

    @Override
    public PageData<DictType> findAll(DictType query, PageData<DictType> pageable) {
        return dictTypeRepo.findByFilter(query, pageable);
    }

    private void validate(DictType dictType) {
        dictTypeRepo.findByDictType(dictType.getDictType()).ifPresent(exist -> {
            if (dictType.getId() == null || !Objects.equals(exist.getId(), dictType.getId())) {
                throw new BusinessException("字典类型已存在");
            }
        });
    }

    @Transactional
    @Override
    public DictType create(DictType dictType) {
        validate(dictType);
        return dictTypeRepo.save(dictType);
    }

    @Transactional
    @Override
    public DictType update(DictType dictType) {
        validate(dictType);
        DictType item = dictTypeRepo.findById(dictType.getId()).orElseThrow(NotFoundException::new);
        item.copyNonNulls(dictType);
        return dictTypeRepo.save(item);
    }

    @Transactional
    @Override
    public void delete(String id) {
        dictTypeRepo.deleteById(id);
    }

    @Transactional
    @Override
    public void delete(List<String> id) {
        dictTypeRepo.deleteAllByIdInBatch(id);
    }
}
