package i.g.sbl.sky.controller;

import i.g.sbl.sky.basic.utils.CodeGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

@Tag(name = "Common")
@RestController
public class IndexController {
    @Autowired
    private DataSource dataSource;

    @Operation(summary = "genCode")
    @PostMapping("genCode")
    public void genCode(@RequestBody CodeGenerator request) {
        CodeGenerator generator = new CodeGenerator();
        generator.setDataSource(dataSource);
        generator.generate();
    }
}
