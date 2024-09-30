package i.g.sbl.sky.controller.system;

import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.basic.model.ResponseData;
import i.g.sbl.sky.entity.system.Role;
import i.g.sbl.sky.service.system.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "system/role", description = "角色表")
@RequestMapping("system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Operation(summary = "分页查询")
    @GetMapping
    public ResponseData<PageData<Role>> getPage(
            @RequestParam(name = "pageIndex", defaultValue = "1") int pageIndex,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ) {
        Role role = new Role();

        PageData<Role> page = roleService.findAll(role, PageData.of(pageIndex, pageSize));
        return ResponseData.success(page);
    }

    @Operation(summary = "根据ID获取")
    @GetMapping("{id}")
    public ResponseData<Role> getById(@PathVariable("id") String id) {
        Optional<Role> role = roleService.findById(id);
        return ResponseData.success(role);
    }

    @Operation(summary = "创建")
    @PostMapping
    public ResponseData<Role> create(@RequestBody Role role) {
        Role created = roleService.create(role);
        return ResponseData.success(created);
    }

    @Operation(summary = "更新")
    @PutMapping
    public ResponseData<Role> update(@RequestBody Role role) {
        Role updated = roleService.update(role);
        return ResponseData.success(updated);
    }

    @Operation(summary = "删除")
    @DeleteMapping("{id}")
    public ResponseData<Void> delete(@PathVariable("id") String id) {
        roleService.delete(id);
        return ResponseData.success();
    }

    @Operation(summary = "批量删除")
    @DeleteMapping()
    public ResponseData<Void> delete(@RequestParam(name = "id") List<String> id) {
        roleService.delete(id);
        return ResponseData.success();
    }
}
