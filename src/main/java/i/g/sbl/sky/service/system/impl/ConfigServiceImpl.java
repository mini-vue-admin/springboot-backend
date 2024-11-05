package i.g.sbl.sky.service.system.impl;

import i.g.sbl.sky.basic.exception.BusinessException;
import i.g.sbl.sky.basic.exception.NotFoundException;
import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.Config;
import i.g.sbl.sky.repo.system.ConfigRepo;
import i.g.sbl.sky.service.system.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ConfigServiceImpl implements ConfigService {
    @Autowired
    private ConfigRepo configRepo;


    @Override
    public Optional<Config> findById(String id) {
        return configRepo.findById(id);
    }

    @Override
    public List<Config> findAll(Config query) {
        return configRepo.findByFilter(query);
    }

    @Override
    public PageData<Config> findAll(Config query, PageData<Config> pageable) {
        return configRepo.findByFilter(query, pageable);
    }

    @Override
    public String getValue(String key,
                           String defaultValue) {
        return configRepo.findByConfigKey(key).map(Config::getConfigValue).orElse(defaultValue);
    }

    private void validate(Config config) {
        configRepo.findByConfigKey(config.getConfigKey()).ifPresent(exist -> {
            if (config.getId() == null || !Objects.equals(exist.getId(), config.getId())) {
                throw new BusinessException("配置键名已存在");
            }
        });
    }

    @Transactional
    @Override
    public Config create(Config config) {
        validate(config);
        return configRepo.save(config);
    }

    @Transactional
    @Override
    public Config update(Config config) {
        validate(config);
        Config item = configRepo.findById(config.getId()).orElseThrow(NotFoundException::new);
        item.copyNonNulls(config);
        return configRepo.save(item);
    }

    @Transactional
    @Override
    public void delete(String id) {
        configRepo.deleteById(id);
    }

    @Transactional
    @Override
    public void delete(List<String> id) {
        configRepo.deleteAllByIdInBatch(id);
    }
}
