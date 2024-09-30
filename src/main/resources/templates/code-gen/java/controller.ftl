package ${MODULE_PACKAGE};

import ${BASE_PACKAGE}.basic.model.PageData;
import ${BASE_PACKAGE}.basic.model.ResponseData;
import ${BASE_PACKAGE}.entity.${MODULE_NAME}.${ENTITY_NAME};
import ${BASE_PACKAGE}.service.${MODULE_NAME}.${ENTITY_NAME}Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "${MODULE_NAME}/${ENTITY_FIELD_NAME}", description = "${TABLE_COMMENT}")
@RequestMapping("${MODULE_NAME}/${ENTITY_FIELD_NAME}")
public class ${ENTITY_NAME}Controller {

    @Autowired
    private ${ENTITY_NAME}Service ${ENTITY_FIELD_NAME}Service;

    @Operation(summary = "分页查询")
    @GetMapping
    public ResponseData<PageData<${ENTITY_NAME}>> getPage(
            @RequestParam(name = "pageIndex", defaultValue = "1") int pageIndex,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ) {
        ${ENTITY_NAME} ${ENTITY_FIELD_NAME} = new ${ENTITY_NAME}();

        PageData<${ENTITY_NAME}> page = ${ENTITY_FIELD_NAME}Service.findAll(${ENTITY_FIELD_NAME}, PageData.of(pageIndex, pageSize));
        return ResponseData.success(page);
    }

    @Operation(summary = "根据ID获取")
    @GetMapping("{id}")
    public ResponseData<${ENTITY_NAME}> getById(@PathVariable("id") String id) {
        Optional<${ENTITY_NAME}> ${ENTITY_FIELD_NAME} = ${ENTITY_FIELD_NAME}Service.findById(id);
        return ResponseData.success(${ENTITY_FIELD_NAME});
    }

    @Operation(summary = "创建")
    @PostMapping
    public ResponseData<${ENTITY_NAME}> create(@RequestBody ${ENTITY_NAME} ${ENTITY_FIELD_NAME}) {
        ${ENTITY_NAME} created = ${ENTITY_FIELD_NAME}Service.create(${ENTITY_FIELD_NAME});
        return ResponseData.success(created);
    }

    @Operation(summary = "更新")
    @PutMapping
    public ResponseData<${ENTITY_NAME}> update(@RequestBody ${ENTITY_NAME} ${ENTITY_FIELD_NAME}) {
        ${ENTITY_NAME} updated = ${ENTITY_FIELD_NAME}Service.update(${ENTITY_FIELD_NAME});
        return ResponseData.success(updated);
    }

    @Operation(summary = "删除")
    @DeleteMapping("{id}")
    public ResponseData<Void> delete(@PathVariable("id") String id) {
        ${ENTITY_FIELD_NAME}Service.delete(id);
        return ResponseData.success();
    }

    @Operation(summary = "批量删除")
    @DeleteMapping()
    public ResponseData<Void> delete(@RequestParam(name = "id") List<String> id) {
        ${ENTITY_FIELD_NAME}Service.delete(id);
        return ResponseData.success();
    }
}
