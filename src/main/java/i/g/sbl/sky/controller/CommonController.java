package i.g.sbl.sky.controller;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import i.g.sbl.sky.basic.model.ResponseData;
import i.g.sbl.sky.basic.utils.CodeGenerator;
import i.g.sbl.sky.entity.system.QUser;
import i.g.sbl.sky.entity.system.RoleUser;
import i.g.sbl.sky.entity.system.User;
import i.g.sbl.sky.repo.system.UserRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

import static i.g.sbl.sky.entity.system.QRoleUser.roleUser;
import static i.g.sbl.sky.entity.system.QUser.user;

@Tag(name = "Common", description = "公共接口")
@RestController
public class CommonController {
    @Autowired
    private DataSource dataSource;

    @Operation(summary = "健康状态")
    @GetMapping("healthy")
    public ResponseData<Void> healthy() {
        return ResponseData.success();
    }

    @Operation(summary = "genCode")
    @PostMapping("genCode")
    public void genCode(@RequestBody CodeGenerator generator) {
        generator.setDataSource(dataSource);
        generator.generate();
    }

    @Autowired
    private UserRepo userRepo;

    @Operation(summary = "userRoles")
    @GetMapping("userRoles")
    public Object userRoles() {
        BooleanBuilder predicates = new BooleanBuilder();
        predicates = predicates.and(user.username.eq("admin"));
        Iterable<Tuple> all = userRepo.findAny(
                new JPAQuery<Tuple>().select(user, roleUser).from(user)
                        .leftJoin(roleUser)
                        .on(roleUser.userId.eq(user.id))
                        .where(user.id.eq(1L))
        );

        for (Tuple tuple : all) {
            for (int i = 0; i < tuple.size(); i++) {
                System.out.println(tuple.get(0, User.class));
                System.out.println(tuple.get(1, RoleUser.class));
            }
        }
        return null;
    }
}
