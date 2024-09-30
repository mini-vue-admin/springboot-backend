package i.g.sbl.sky.service.system;

import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.Config;

import java.util.List;
import java.util.Optional;

public interface ConfigService {

    Optional<Config> findById(String id);

    List<Config> findAll(Config query);

    PageData<Config> findAll(Config query, PageData<Config> pageable);

    /**
     * 根据键名获取配置值
     *
     * @param key          配置键名
     * @param defaultValue 当找不到时，返回该默认值
     * @return 配置值
     */
    String getValue(String key, String defaultValue);

    Config create(Config config);

    Config update(Config config);

    void delete(String id);

    void delete(List<String> id);

}
