package i.g.sbl.sky.service.system.impl;

import i.g.sbl.sky.basic.exception.NotFoundException;
import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.DictData;
import i.g.sbl.sky.repo.system.DictDataRepo;
import i.g.sbl.sky.service.system.DictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DictDataServiceImpl implements DictDataService {
    @Autowired
    private DictDataRepo dictDataRepo;


    @Override
    public Optional<DictData> findById(String id) {
        return dictDataRepo.findById(id);
    }

    @Override
    public List<DictData> findAll(DictData query) {
        return dictDataRepo.findByFilter(query);
    }

    @Override
    public PageData<DictData> findAll(DictData query, PageData<DictData> pageable) {
        return dictDataRepo.findByFilter(query, pageable);
    }

    @Transactional
    @Override
    public DictData create(DictData dictData) {
        return dictDataRepo.save(dictData);
    }

    @Transactional
    @Override
    public DictData update(DictData dictData) {
        DictData item = dictDataRepo.findById(dictData.getId()).orElseThrow(NotFoundException::new);
        item.copyNonNulls(dictData);
        return dictDataRepo.save(item);
    }

    @Transactional
    @Override
    public void delete(String id) {
        dictDataRepo.deleteById(id);
    }

    @Transactional
    @Override
    public void delete(List<String> id) {
        dictDataRepo.deleteAllByIdInBatch(id);
    }
}
