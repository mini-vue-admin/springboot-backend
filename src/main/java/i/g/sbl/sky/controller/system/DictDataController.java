package i.g.sbl.sky.controller.system;

import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.basic.model.ResponseData;
import i.g.sbl.sky.entity.system.DictData;
import i.g.sbl.sky.service.system.DictDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "system/dictData", description = "字典数据表")
@RequestMapping("system/dictData")
public class DictDataController {

    @Autowired
    private DictDataService dictDataService;

    @Operation(summary = "列表查询")
    @GetMapping
    public ResponseData<List<DictData>> getList() {
        DictData dictData = new DictData();

        List<DictData> list = dictDataService.findAll(dictData);
        return ResponseData.success(list);
    }

    @Operation(summary = "分页查询")
    @GetMapping("page")
    public ResponseData<PageData<DictData>> getPage(
            @RequestParam(name = "pageIndex", defaultValue = "1") int pageIndex,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ) {
        DictData dictData = new DictData();

        PageData<DictData> page = dictDataService.findAll(dictData, PageData.of(pageIndex, pageSize));
        return ResponseData.success(page);
    }

    @Operation(summary = "根据ID获取")
    @GetMapping("{id:\\d+}")
    public ResponseData<DictData> getById(@PathVariable("id") Long id) {
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
    @DeleteMapping("{id:\\d+}")
    public ResponseData<Void> delete(@PathVariable("id") Long id) {
        dictDataService.delete(id);
        return ResponseData.success();
    }

    @Operation(summary = "批量删除")
    @DeleteMapping()
    public ResponseData<Void> delete(@RequestParam(name = "id") List<Long> id) {
        dictDataService.delete(id);
        return ResponseData.success();
    }
}
