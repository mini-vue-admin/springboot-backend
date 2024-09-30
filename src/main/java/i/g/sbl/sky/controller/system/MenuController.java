package i.g.sbl.sky.controller.system;

import i.g.sbl.sky.basic.cons.system.MenuType;
import i.g.sbl.sky.basic.cons.system.Status;
import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.basic.model.ResponseData;
import i.g.sbl.sky.entity.system.Menu;
import i.g.sbl.sky.entity.system.vo.MenuQuery;
import i.g.sbl.sky.service.system.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "system/menu", description = "菜单表")
@RequestMapping("system/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Operation(summary = "分页查询")
    @GetMapping
    public ResponseData<PageData<Menu>> getPage(
            @Parameter(description = "页号", required = true) @RequestParam(name = "pageIndex", defaultValue = "1") int pageIndex,
            @Parameter(description = "分页大小", required = true) @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @Parameter(description = "排序字段") @RequestParam(name = "sortField", defaultValue = "orderNum", required = false) String sortField,
            @Parameter(description = "排序方向") @RequestParam(name = "sortOrder", defaultValue = "ASC", required = false) Sort.Direction sortOrder,
            @Parameter(description = "上级菜单ID") @RequestParam(name = "parentId", required = false) String parentId,
            @Parameter(description = "菜单标题，模糊查询") @RequestParam(name = "menuTitle", required = false) String menuTitle,
            @Parameter(description = "菜单类型") @RequestParam(name = "menuType", required = false) MenuType menuType,
            @Parameter(description = "菜单名称，模糊查询") @RequestParam(name = "menuName", required = false) String menuName,
            @Parameter(description = "菜单路径，模糊查询") @RequestParam(name = "path", required = false) String path,
            @Parameter(description = "组件路径，模糊查询") @RequestParam(name = "component", required = false) String component,
            @Parameter(description = "状态") @RequestParam(name = "status", required = false) Status status,
            @Parameter(description = "是否递归返回所有子节点") @RequestParam(name = "childRecursion", required = false, defaultValue = "false") Boolean childRecursion
    ) {
        MenuQuery menu = new MenuQuery();
        menu.setParentId(parentId);
        menu.setMenuTitle(menuTitle);
        menu.setMenuType(menuType);
        menu.setMenuName(menuName);
        menu.setPath(path);
        menu.setComponent(component);
        menu.setStatus(status);
        menu.setChildRecursion(childRecursion);

        PageData<Menu> page = menuService.findAll(menu, PageData.of(pageIndex, pageSize, sortField, sortOrder));
        return ResponseData.success(page);
    }

    @Operation(summary = "根据ID获取")
    @GetMapping("{id}")
    public ResponseData<Menu> getById(@PathVariable("id") String id) {
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
    @DeleteMapping("{id}")
    public ResponseData<Void> delete(@PathVariable("id") String id) {
        menuService.delete(id);
        return ResponseData.success();
    }

    @Operation(summary = "批量删除")
    @DeleteMapping()
    public ResponseData<Void> delete(@RequestParam(name = "id") List<String> id) {
        menuService.delete(id);
        return ResponseData.success();
    }
}
