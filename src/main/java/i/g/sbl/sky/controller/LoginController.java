package i.g.sbl.sky.controller;

import i.g.sbl.sky.basic.model.DetailedUser;
import i.g.sbl.sky.basic.model.LoginRequest;
import i.g.sbl.sky.basic.model.ResponseData;
import i.g.sbl.sky.basic.utils.Assert;
import i.g.sbl.sky.basic.utils.JwtUtils;
import i.g.sbl.sky.service.system.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Tag(name = "Authentication")
@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;


    @Operation(summary = "login")
    @PostMapping("login")
    public ResponseData<String> login(@RequestBody LoginRequest loginRequest) {
        userService.validatePassword(loginRequest.getUsername(), loginRequest.getPassword());
        Optional<DetailedUser> user = userService.getDetailedUserByUsername(loginRequest.getUsername());
        Assert.isTrue(user.isPresent(), "User not found");
        String token = jwtUtils.encode(user.get().getUsername());
        return ResponseData.success(token);
    }
}
