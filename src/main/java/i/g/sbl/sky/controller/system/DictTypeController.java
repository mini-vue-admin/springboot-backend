package i.g.sbl.sky.controller.system;

import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.basic.model.ResponseData;
import i.g.sbl.sky.entity.system.DictType;
import i.g.sbl.sky.service.system.DictTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
            @Parameter(description = "页号", required = true) @RequestParam(name = "pageIndex", defaultValue = "1") int pageIndex,
            @Parameter(description = "分页大小", required = true) @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @Parameter(description = "排序字段") @RequestParam(name = "sortField", defaultValue = "updateTime", required = false) String sortField,
            @Parameter(description = "排序方向") @RequestParam(name = "sortOrder", defaultValue = "DESC", required = false) Sort.Direction sortOrder,
            @Parameter(description = "字典类型，模糊查询") @RequestParam(name = "dictType", required = false) String dictType,
            @Parameter(description = "字典名称，模糊查询") @RequestParam(name = "dictName", required = false) String dictName
    ) {
        DictType type = new DictType();
        type.setDictType(dictType);
        type.setDictName(dictName);

        PageData<DictType> page = dictTypeService.findAll(type, PageData.of(pageIndex, pageSize, sortField, sortOrder));
        return ResponseData.success(page);
    }

    @Operation(summary = "根据ID获取")
    @GetMapping("{id}")
    public ResponseData<DictType> getById(@PathVariable("id") String id) {
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
    @DeleteMapping("{id}")
    public ResponseData<Void> delete(@PathVariable("id") String id) {
        dictTypeService.delete(id);
        return ResponseData.success();
    }

    @Operation(summary = "批量删除")
    @DeleteMapping()
    public ResponseData<Void> delete(@RequestParam(name = "id") List<String> id) {
        dictTypeService.delete(id);
        return ResponseData.success();
    }
}
