package i.g.sbl.sky.controller;

import i.g.sbl.sky.basic.model.DetailedUser;
import i.g.sbl.sky.basic.model.LoginRequest;
import i.g.sbl.sky.basic.model.ResponseData;
import i.g.sbl.sky.service.system.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication")
@RestController
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "login")
    @PostMapping("login")
    public ResponseData<DetailedUser> login(@RequestBody LoginRequest loginRequest) {
        userService.validate(loginRequest.getUsername(), loginRequest.getPassword());
        DetailedUser user = userService.getDetailedUserByUsername(loginRequest.getUsername());
        return ResponseData.success(user);
    }
}
