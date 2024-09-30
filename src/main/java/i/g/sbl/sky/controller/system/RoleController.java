package i.g.sbl.sky.controller.system;

import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.basic.model.ResponseData;
import i.g.sbl.sky.entity.system.Menu;
import i.g.sbl.sky.entity.system.Role;
import i.g.sbl.sky.entity.system.User;
import i.g.sbl.sky.entity.system.vo.MemberIds;
import i.g.sbl.sky.entity.system.vo.MenuQuery;
import i.g.sbl.sky.entity.system.vo.UserQuery;
import i.g.sbl.sky.service.system.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "sortField", defaultValue = "updateTime", required = false) String sortField,
            @RequestParam(name = "sortOrder", defaultValue = "DESC", required = false) Sort.Direction sortOrder,
            @RequestParam(name="roleKey" ,required=false) String roleKey,
            @RequestParam(name = "roleName", required = false) String roleName
    ) {
        Role role = new Role();
        role.setRoleKey(roleKey);
        role.setRoleName(roleName);

        PageData<Role> page = roleService.findAll(role, PageData.of(pageIndex, pageSize, sortField, sortOrder));
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

    @Operation(summary = "成员列表-分页查询")
    @GetMapping("{id}/members")
    public ResponseData<PageData<User>> getMemberPage(
            @PathVariable("id") String id,
            @RequestParam(name = "pageIndex", defaultValue = "1") int pageIndex,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ) {
        UserQuery query = new UserQuery();
        query.setRoleId(id);
        PageData<User> page = roleService.findMemberPage(query, PageData.of(pageIndex, pageSize));
        return ResponseData.success(page);
    }

    @Operation(summary = "成员列表-新增")
    @PostMapping("{id}/members")
    public ResponseData<Void> addMembers(
            @PathVariable("id") String id,
            @RequestBody MemberIds memberIds
    ) {
        roleService.addMembers(id, memberIds);
        return ResponseData.success();
    }

    @Operation(summary = "成员列表-删除")
    @DeleteMapping("{id}/members")
    public ResponseData<Void> deleteMembers(
            @PathVariable("id") String id,
            @RequestBody MemberIds memberIds
    ) {
        roleService.deleteMember(id, memberIds);
        return ResponseData.success();
    }


    @Operation(summary = "角色菜单-分页查询")
    @GetMapping("{id}/menus")
    public ResponseData<PageData<Menu>> getMenuPage(
            @PathVariable("id") String id,
            @RequestParam(name = "pageIndex", defaultValue = "1") int pageIndex,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "sortField", defaultValue = "orderNum", required = false) String sortField,
            @RequestParam(name = "sortOrder", defaultValue = "ASC", required = false) Sort.Direction sortOrder
    ) {
        MenuQuery query = new MenuQuery();
        query.setRoleId(id);
        PageData<Menu> page = roleService.findMenuPage(query, PageData.of(pageIndex, pageSize, sortField, sortOrder));
        return ResponseData.success(page);
    }

    @Operation(summary = "角色菜单-新增")
    @PostMapping("{id}/menus")
    public ResponseData<Void> addMenus(
            @PathVariable("id") String id,
            @RequestBody MemberIds memberIds
    ) {
        roleService.addMenu(id, memberIds);
        return ResponseData.success();
    }

    @Operation(summary = "角色菜单-删除")
    @DeleteMapping("{id}/menus")
    public ResponseData<Void> deleteMenus(
            @PathVariable("id") String id,
            @RequestBody MemberIds memberIds
    ) {
        roleService.deleteMenu(id, memberIds);
        return ResponseData.success();
    }
}
