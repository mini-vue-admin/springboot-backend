package i.g.sbl.sky.controller.system;

import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.basic.model.ResponseData;
import i.g.sbl.sky.basic.utils.CodeGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import net.lingala.zip4j.ZipFile;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@Tag(name = "system/genCode", description = "代码生成")
@RequestMapping("system/genCode")
public class GenCodeController {

    @Autowired
    private DataSource dataSource;

    @Operation(summary = "分页查询数据库表结构")
    @GetMapping
    public ResponseData<PageData<CodeGenerator.TableInfo>> getPage(
            @Parameter(description = "页号", required = true) @RequestParam(name = "pageIndex", defaultValue = "1") int pageIndex,
            @Parameter(description = "分页大小", required = true) @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @Parameter(description = "排序字段") @RequestParam(name = "sortField", defaultValue = "tableName", required = false) String sortField,
            @Parameter(description = "排序方向") @RequestParam(name = "sortOrder", defaultValue = "ASC", required = false) Sort.Direction sortOrder,
            @Parameter(description = "表名，模糊查询") @RequestParam(name = "tableName", required = false) String tableName
    ) {
        CodeGenerator generator = new CodeGenerator(dataSource);
        PageData<CodeGenerator.TableInfo> tables = generator.getTables(tableName, PageData.of(pageIndex, pageSize, sortField, sortOrder));
        return ResponseData.success(tables);
    }

    @SneakyThrows
    @Operation(summary = "生成代码压缩包")
    @PostMapping()
    public void genCode(@RequestBody CodeGenerator generator, HttpServletResponse response) {
        Path zipPath = Files.createTempFile("codeGen", ".zip");
        Path tmpDir = Files.createTempDirectory("codeGen");

        generator.setOutputDir(tmpDir.toAbsolutePath().toString());
        generator.setRemoveTableNamePrefix(true);
        generator.setDataSource(dataSource);
        generator.generate();

        new ZipFile(zipPath.toFile()).addFolder(tmpDir.toFile());
        response.reset();
        response.setContentLength((int) zipPath.toFile().length());
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", STR."attachment;filename=\{URLEncoder.encode(zipPath.getFileName().toString(), StandardCharsets.UTF_8)}");
        try (
                InputStream input = Files.newInputStream(zipPath);
                OutputStream outputStream = response.getOutputStream()) {
            IOUtils.copy(input, outputStream);
        }
    }
}
