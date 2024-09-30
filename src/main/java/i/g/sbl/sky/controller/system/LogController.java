package i.g.sbl.sky.controller.system;

import i.g.sbl.sky.basic.cons.system.Level;
import i.g.sbl.sky.basic.cons.system.Type;
import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.basic.model.ResponseData;
import i.g.sbl.sky.entity.system.Log;
import i.g.sbl.sky.service.system.LogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
            @Parameter(description = "页号", required = true) @RequestParam(name = "pageIndex", defaultValue = "1") int pageIndex,
            @Parameter(description = "分页大小", required = true) @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @Parameter(description = "排序字段") @RequestParam(name = "sortField", defaultValue = "updateTime", required = false) String sortField,
            @Parameter(description = "排序方向") @RequestParam(name = "sortOrder", defaultValue = "DESC", required = false) Sort.Direction sortOrder,
            @Parameter(description = "日志类型") @RequestParam(name = "type", required = false) Type type,
            @Parameter(description = "日志级别") @RequestParam(name = "level", required = false) Level level,
            @Parameter(description = "日志内容，模糊查询") @RequestParam(name = "msg", required = false) String msg,
            @Parameter(description = "用户名，模糊查询") @RequestParam(name = "username", required = false) String username,
            @Parameter(description = "用户昵称，模糊查询") @RequestParam(name = "nickname", required = false) String nickname
    ) {
        Log log = new Log();
        log.setMsg(msg);
        log.setLevel(level);
        log.setUsername(username);
        log.setNickname(nickname);
        log.setType(type);

        PageData<Log> page = logService.findAll(log, PageData.of(pageIndex, pageSize, sortField, sortOrder));
        return ResponseData.success(page);
    }

    @Operation(summary = "根据ID获取")
    @GetMapping("{id}")
    public ResponseData<Log> getById(@PathVariable("id") String id) {
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
    @DeleteMapping("{id}")
    public ResponseData<Void> delete(@PathVariable("id") String id) {
        logService.delete(id);
        return ResponseData.success();
    }

    @Operation(summary = "批量删除")
    @DeleteMapping()
    public ResponseData<Void> delete(@RequestParam(name = "id") List<String> id) {
        logService.delete(id);
        return ResponseData.success();
    }
}
