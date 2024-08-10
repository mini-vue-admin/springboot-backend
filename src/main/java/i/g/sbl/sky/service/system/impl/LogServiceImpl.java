package i.g.sbl.sky.service.system.impl;

import i.g.sbl.sky.basic.exception.NotFoundException;
import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.Log;
import i.g.sbl.sky.repo.system.LogRepo;
import i.g.sbl.sky.service.system.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogRepo logRepo;


    @Override
    public Optional<Log> findById(Long id) {
        return logRepo.findById(id);
    }

    @Override
    public List<Log> findAll(Log query) {
        return logRepo.findByFilter(query);
    }

    @Override
    public PageData<Log> findAll(Log query, PageData<Log> pageable) {
        return logRepo.findByFilter(query, pageable);
    }

    @Transactional
    @Override
    public Log create(Log log) {
        return logRepo.save(log);
    }

    @Transactional
    @Override
    public Log update(Log log) {
        Log item = logRepo.findById(log.getId()).orElseThrow(NotFoundException::new);
        item.copyNonNulls(log);
        return logRepo.save(item);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        logRepo.deleteById(id);
    }

    @Transactional
    @Override
    public void delete(List<Long> id) {
        logRepo.deleteAllById(id);
    }
}
