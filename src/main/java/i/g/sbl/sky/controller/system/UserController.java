package i.g.sbl.sky.controller.system;

import i.g.sbl.sky.basic.cons.system.Status;
import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.basic.model.ResponseData;
import i.g.sbl.sky.entity.system.User;
import i.g.sbl.sky.entity.system.vo.UserQuery;
import i.g.sbl.sky.service.system.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "system/user", description = "用户表")
@RequestMapping("system/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "分页查询")
    @GetMapping
    public ResponseData<PageData<User>> getPage(
            @RequestParam(name = "pageIndex", defaultValue = "1") int pageIndex,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "sortField", defaultValue = "updateTime", required = false) String sortField,
            @RequestParam(name = "sortOrder", defaultValue = "DESC", required = false) Sort.Direction sortOrder,
            @RequestParam(name = "username", required = false) String username,
            @RequestParam(name = "nickname", required = false) String nickname,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "phone", required = false) String phone,
            @RequestParam(name = "status", required = false) Status status,
            @RequestParam(name = "keyword", required = false) String keyword
    ) {
        UserQuery user = new UserQuery();
        user.setUsername(username);
        user.setNickname(nickname);
        user.setEmail(email);
        user.setPhone(phone);
        user.setStatus(status);
        user.setKeyword(keyword);

        PageData<User> page = userService.findAll(user, PageData.of(pageIndex, pageSize, sortField, sortOrder));
        return ResponseData.success(page);
    }

    @Operation(summary = "根据ID获取")
    @GetMapping("{id}")
    public ResponseData<User> getById(@PathVariable("id") String id) {
        Optional<User> user = userService.findById(id);
        return ResponseData.success(user);
    }

    @Operation(summary = "创建")
    @PostMapping
    public ResponseData<User> create(@RequestBody User user) {
        User created = userService.create(user);
        return ResponseData.success(created);
    }

    @Operation(summary = "更新")
    @PutMapping
    public ResponseData<User> update(@RequestBody User user) {
        User updated = userService.update(user);
        return ResponseData.success(updated);
    }

    @Operation(summary = "重置密码")
    @PutMapping("{id}/resetPassword")
    public ResponseData<Void> resetPassword(@PathVariable("id") String id) {
        userService.resetPassword(id);
        return ResponseData.success();
    }

    @Operation(summary = "删除")
    @DeleteMapping("{id}")
    public ResponseData<Void> delete(@PathVariable("id") String id) {
        userService.delete(id);
        return ResponseData.success();
    }

    @Operation(summary = "批量删除")
    @DeleteMapping()
    public ResponseData<Void> delete(@RequestParam(name = "id") List<String> id) {
        userService.delete(id);
        return ResponseData.success();
    }
}
