package i.g.sbl.sky.controller.system;

import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.basic.model.ResponseData;
import i.g.sbl.sky.entity.system.DictData;
import i.g.sbl.sky.service.system.DictDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "system/dictData", description = "字典数据表")
@RequestMapping("system/dictData")
public class DictDataController {

    @Autowired
    private DictDataService dictDataService;

    @Operation(summary = "分页查询")
    @GetMapping
    public ResponseData<PageData<DictData>> getPage(
            @Parameter(description = "页号", required = true) @RequestParam(name = "pageIndex", defaultValue = "1") int pageIndex,
            @Parameter(description = "分页大小", required = true) @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @Parameter(description = "排序字段") @RequestParam(name = "sortField", defaultValue = "updateTime", required = false) String sortField,
            @Parameter(description = "排序方向") @RequestParam(name = "sortOrder", defaultValue = "DESC", required = false) Sort.Direction sortOrder,
            @Parameter(description = "字典类型，精确查询") @RequestParam(name = "dictType", required = false) String dictType,
            @Parameter(description = "字典标签，模糊查询") @RequestParam(name = "dictLabel", required = false) String dictLabel,
            @Parameter(description = "字典键值，精确查询") @RequestParam(name = "dictValue", required = false) String dictValue
    ) {
        DictData dictData = new DictData();
        dictData.setDictLabel(dictLabel);
        dictData.setDictValue(dictValue);
        dictData.setDictType(dictType);

        PageData<DictData> page = dictDataService.findAll(dictData, PageData.of(pageIndex, pageSize, sortField, sortOrder));
        return ResponseData.success(page);
    }

    @Operation(summary = "根据ID获取")
    @GetMapping("{id}")
    public ResponseData<DictData> getById(@PathVariable("id") String id) {
        Optional<DictData> dictData = dictDataService.findById(id);
        return ResponseData.success(dictData);
    }

    @Operation(summary = "创建")
    @PostMapping
    public ResponseData<DictData> create(@RequestBody DictData dictData) {
        DictData created = dictDataService.create(dictData);
        return ResponseData.success(created);
    }

    @Operation(summary = "更新")
    @PutMapping
    public ResponseData<DictData> update(@RequestBody DictData dictData) {
        DictData updated = dictDataService.update(dictData);
        return ResponseData.success(updated);
    }

    @Operation(summary = "删除")
    @DeleteMapping("{id}")
    public ResponseData<Void> delete(@PathVariable("id") String id) {
        dictDataService.delete(id);
        return ResponseData.success();
    }

    @Operation(summary = "批量删除")
    @DeleteMapping()
    public ResponseData<Void> delete(@RequestParam(name = "id") List<String> id) {
        dictDataService.delete(id);
        return ResponseData.success();
    }
}
