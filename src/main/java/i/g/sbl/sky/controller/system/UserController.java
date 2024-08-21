package i.g.sbl.sky.controller.system;

import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.basic.model.ResponseData;
import i.g.sbl.sky.entity.system.User;
import i.g.sbl.sky.service.system.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ) {
        User user = new User();

        PageData<User> page = userService.findAll(user, PageData.of(pageIndex, pageSize));
        return ResponseData.success(page);
    }

    @Operation(summary = "根据ID获取")
    @GetMapping("{id:\\d+}")
    public ResponseData<User> getById(@PathVariable("id") Long id) {
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

    @Operation(summary = "删除")
    @DeleteMapping("{id:\\d+}")
    public ResponseData<Void> delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseData.success();
    }

    @Operation(summary = "批量删除")
    @DeleteMapping()
    public ResponseData<Void> delete(@RequestParam(name = "id") List<Long> id) {
        userService.delete(id);
        return ResponseData.success();
    }
}
