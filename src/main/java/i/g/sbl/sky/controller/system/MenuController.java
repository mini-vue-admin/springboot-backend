package i.g.sbl.sky.controller.system;

import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.basic.model.ResponseData;
import i.g.sbl.sky.entity.system.Menu;
import i.g.sbl.sky.service.system.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "system/menu", description = "菜单表")
@RequestMapping("system/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Operation(summary = "列表查询")
    @GetMapping
    public ResponseData<List<Menu>> getList() {
        Menu menu = new Menu();

        List<Menu> list = menuService.findAll(menu);
        return ResponseData.success(list);
    }

    @Operation(summary = "分页查询")
    @GetMapping("page")
    public ResponseData<PageData<Menu>> getPage(
            @RequestParam(name = "pageIndex", defaultValue = "1") int pageIndex,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ) {
        Menu menu = new Menu();

        PageData<Menu> page = menuService.findAll(menu, PageData.of(pageIndex, pageSize));
        return ResponseData.success(page);
    }

    @Operation(summary = "根据ID获取")
    @GetMapping("{id:\\d+}")
    public ResponseData<Menu> getById(@PathVariable("id") Long id) {
        Optional<Menu> menu = menuService.findById(id);
        return ResponseData.success(menu);
    }

    @Operation(summary = "创建")
    @PostMapping
    public ResponseData<Menu> create(@RequestBody Menu menu) {
        Menu created = menuService.create(menu);
        return ResponseData.success(created);
    }

    @Operation(summary = "更新")
    @PutMapping
    public ResponseData<Menu> update(@RequestBody Menu menu) {
        Menu updated = menuService.update(menu);
        return ResponseData.success(updated);
    }

    @Operation(summary = "删除")
    @DeleteMapping("{id:\\d+}")
    public ResponseData<Void> delete(@PathVariable("id") Long id) {
        menuService.delete(id);
        return ResponseData.success();
    }

    @Operation(summary = "批量删除")
    @DeleteMapping()
    public ResponseData<Void> delete(@RequestParam(name = "id") List<Long> id) {
        menuService.delete(id);
        return ResponseData.success();
    }
}
