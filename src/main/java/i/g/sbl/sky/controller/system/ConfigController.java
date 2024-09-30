package i.g.sbl.sky.controller.system;

import i.g.sbl.sky.basic.cons.system.ConfigType;
import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.basic.model.ResponseData;
import i.g.sbl.sky.entity.system.Config;
import i.g.sbl.sky.service.system.ConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "system/config", description = "参数配置表")
@RequestMapping("system/config")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @Operation(summary = "分页查询")
    @GetMapping
    public ResponseData<PageData<Config>> getPage(
            @RequestParam(name = "pageIndex", defaultValue = "1") int pageIndex,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "configKey", required = false) String configKey,
            @RequestParam(name = "configName", required = false) String configName,
            @RequestParam(name = "configType", required = false) ConfigType configType
    ) {
        Config config = new Config();
        config.setConfigKey(configKey);
        config.setConfigName(configName);
        config.setConfigType(configType);

        PageData<Config> page = configService.findAll(config, PageData.of(pageIndex, pageSize));
        return ResponseData.success(page);
    }

    @Operation(summary = "根据ID获取")
    @GetMapping("{id}")
    public ResponseData<Config> getById(@PathVariable("id") String id) {
        Optional<Config> config = configService.findById(id);
        return ResponseData.success(config);
    }

    @Operation(summary = "创建")
    @PostMapping
    public ResponseData<Config> create(@RequestBody Config config) {
        Config created = configService.create(config);
        return ResponseData.success(created);
    }

    @Operation(summary = "更新")
    @PutMapping
    public ResponseData<Config> update(@RequestBody Config config) {
        Config updated = configService.update(config);
        return ResponseData.success(updated);
    }

    @Operation(summary = "删除")
    @DeleteMapping("{id}")
    public ResponseData<Void> delete(@PathVariable("id") String id) {
        configService.delete(id);
        return ResponseData.success();
    }

    @Operation(summary = "批量删除")
    @DeleteMapping()
    public ResponseData<Void> delete(@RequestParam(name = "id") List<String> id) {
        configService.delete(id);
        return ResponseData.success();
    }
}
