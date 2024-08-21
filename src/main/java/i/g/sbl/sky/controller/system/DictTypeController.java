package i.g.sbl.sky.controller.system;

import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.basic.model.ResponseData;
import i.g.sbl.sky.entity.system.DictType;
import i.g.sbl.sky.service.system.DictTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "system/dictType", description = "字典类型表")
@RequestMapping("system/dictType")
public class DictTypeController {

    @Autowired
    private DictTypeService dictTypeService;

    @Operation(summary = "分页查询")
    @GetMapping
    public ResponseData<PageData<DictType>> getPage(
            @RequestParam(name = "pageIndex", defaultValue = "1") int pageIndex,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ) {
        DictType dictType = new DictType();

        PageData<DictType> page = dictTypeService.findAll(dictType, PageData.of(pageIndex, pageSize));
        return ResponseData.success(page);
    }

    @Operation(summary = "根据ID获取")
    @GetMapping("{id:\\d+}")
    public ResponseData<DictType> getById(@PathVariable("id") Long id) {
        Optional<DictType> dictType = dictTypeService.findById(id);
        return ResponseData.success(dictType);
    }

    @Operation(summary = "创建")
    @PostMapping
    public ResponseData<DictType> create(@RequestBody DictType dictType) {
        DictType created = dictTypeService.create(dictType);
        return ResponseData.success(created);
    }

    @Operation(summary = "更新")
    @PutMapping
    public ResponseData<DictType> update(@RequestBody DictType dictType) {
        DictType updated = dictTypeService.update(dictType);
        return ResponseData.success(updated);
    }

    @Operation(summary = "删除")
    @DeleteMapping("{id:\\d+}")
    public ResponseData<Void> delete(@PathVariable("id") Long id) {
        dictTypeService.delete(id);
        return ResponseData.success();
    }

    @Operation(summary = "批量删除")
    @DeleteMapping()
    public ResponseData<Void> delete(@RequestParam(name = "id") List<Long> id) {
        dictTypeService.delete(id);
        return ResponseData.success();
    }
}
