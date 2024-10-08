package i.g.sbl.sky.controller.system;

import i.g.sbl.sky.basic.cons.system.Status;
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
import io.swagger.v3.oas.annotations.Parameter;
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
            @Parameter(description = "页号", required = true) @RequestParam(name = "pageIndex", defaultValue = "1") int pageIndex,
            @Parameter(description = "分页大小", required = true) @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @Parameter(description = "排序字段") @RequestParam(name = "sortField", defaultValue = "updateTime", required = false) String sortField,
            @Parameter(description = "排序方向") @RequestParam(name = "sortOrder", defaultValue = "DESC", required = false) Sort.Direction sortOrder,
            @Parameter(description = "角色键名，精确查询") @RequestParam(name = "roleKey", required = false) String roleKey,
            @Parameter(description = "角色名称，模糊查询") @RequestParam(name = "roleName", required = false) String roleName
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
            @Parameter(description = "页号", required = true) @RequestParam(name = "pageIndex", defaultValue = "1") int pageIndex,
            @Parameter(description = "分页大小", required = true) @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @Parameter(description = "排序字段") @RequestParam(name = "sortField", defaultValue = "updateTime", required = false) String sortField,
            @Parameter(description = "排序方向") @RequestParam(name = "sortOrder", defaultValue = "DESC", required = false) Sort.Direction sortOrder,
            @Parameter(description = "用户名，模糊查询") @RequestParam(name = "username", required = false) String username,
            @Parameter(description = "用户昵称，模糊查询") @RequestParam(name = "nickname", required = false) String nickname,
            @Parameter(description = "邮箱，模糊查询") @RequestParam(name = "email", required = false) String email,
            @Parameter(description = "手机号，模糊查询") @RequestParam(name = "phone", required = false) String phone,
            @Parameter(description = "状态") @RequestParam(name = "status", required = false) Status status,
            @Parameter(description = "关键字，基于用户名、用户昵称、邮箱、手机号多个字段模糊查询") @RequestParam(name = "keyword", required = false) String keyword,
            @Parameter(description = "是否反向角色查询") @RequestParam(name = "roleReverse", required = false, defaultValue = "false") Boolean roleReverse
    ) {
        UserQuery query = new UserQuery();
        query.setRoleId(id);
        query.setUsername(username);
        query.setNickname(nickname);
        query.setEmail(email);
        query.setPhone(phone);
        query.setStatus(status);
        query.setKeyword(keyword);
        query.setRoleReverse(roleReverse);

        PageData<User> page = roleService.findMemberPage(query, PageData.of(pageIndex, pageSize, sortField, sortOrder));
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
            @Parameter(description = "页号", required = true) @RequestParam(name = "pageIndex", defaultValue = "1") int pageIndex,
            @Parameter(description = "分页大小", required = true) @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @Parameter(description = "排序字段") @RequestParam(name = "sortField", defaultValue = "orderNum", required = false) String sortField,
            @Parameter(description = "排序方向") @RequestParam(name = "sortOrder", defaultValue = "ASC", required = false) Sort.Direction sortOrder,
            @Parameter(description = "上级菜单ID") @RequestParam(name = "parentId", required = false) String parentId
    ) {
        MenuQuery query = new MenuQuery();
        query.setRoleId(id);
        query.setParentId(parentId);
        PageData<Menu> page = roleService.findMenuPage(query, PageData.of(pageIndex, pageSize, sortField, sortOrder));
        return ResponseData.success(page);
    }

    @Operation(summary = "角色菜单-创建", description = "注意：该接口会先删除之前的菜单关联")
    @PutMapping("{id}/menus")
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
