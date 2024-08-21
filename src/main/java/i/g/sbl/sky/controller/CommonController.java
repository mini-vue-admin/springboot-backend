package i.g.sbl.sky.controller;

import i.g.sbl.sky.basic.model.ResponseData;
import i.g.sbl.sky.basic.utils.CodeGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

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
}
