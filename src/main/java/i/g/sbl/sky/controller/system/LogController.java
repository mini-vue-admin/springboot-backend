package i.g.sbl.sky.controller.system;

import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.basic.model.ResponseData;
import i.g.sbl.sky.entity.system.Log;
import i.g.sbl.sky.service.system.LogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "system/log", description = "系统审计日志表")
@RequestMapping("system/log")
public class LogController {

    @Autowired
    private LogService logService;

    @Operation(summary = "分页查询")
    @GetMapping
    public ResponseData<PageData<Log>> getPage(
            @RequestParam(name = "pageIndex", defaultValue = "1") int pageIndex,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ) {
        Log log = new Log();

        PageData<Log> page = logService.findAll(log, PageData.of(pageIndex, pageSize));
        return ResponseData.success(page);
    }

    @Operation(summary = "根据ID获取")
    @GetMapping("{id:\\d+}")
    public ResponseData<Log> getById(@PathVariable("id") Long id) {
        Optional<Log> log = logService.findById(id);
        return ResponseData.success(log);
    }

    @Operation(summary = "创建")
    @PostMapping
    public ResponseData<Log> create(@RequestBody Log log) {
        Log created = logService.create(log);
        return ResponseData.success(created);
    }

    @Operation(summary = "更新")
    @PutMapping
    public ResponseData<Log> update(@RequestBody Log log) {
        Log updated = logService.update(log);
        return ResponseData.success(updated);
    }

    @Operation(summary = "删除")
    @DeleteMapping("{id:\\d+}")
    public ResponseData<Void> delete(@PathVariable("id") Long id) {
        logService.delete(id);
        return ResponseData.success();
    }

    @Operation(summary = "批量删除")
    @DeleteMapping()
    public ResponseData<Void> delete(@RequestParam(name = "id") List<Long> id) {
        logService.delete(id);
        return ResponseData.success();
    }
}
