package ${MODULE_PACKAGE};

<#if EXTENDS_BASE_ENTITY>import i.g.sbl.sky.config.jpa.BaseEntity;</#if>
<#if !EXTENDS_BASE_ENTITY>import jakarta.persistence.Id;</#if>
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

<#list ENTITY_PACKAGE_IMPORTS as pkg>
import pkg;
</#list>

/**
* ${TABLE_COMMENT}
*/
@Data
@Entity
@Table(name = "${TABLE_NAME}")
public class ${ENTITY_NAME}<#if EXTENDS_BASE_ENTITY> extends BaseEntity</#if> {
    <#list FIELDS as f>
        <#if f.PRIMARY_KEY>@Id</#if>
        private ${f.FIELD_TYPE} ${f.FIELD_NAME};
    </#list>

    public void copyNonNulls(${ENTITY_NAME} ${ENTITY_FIELD_NAME}) {
        Mapper.INSTANCE.map(${ENTITY_FIELD_NAME}, this);
    }

    @org.mapstruct.Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public interface Mapper {
        Mapper INSTANCE = Mappers.getMapper(${ENTITY_NAME}.Mapper.class);

        void map(${ENTITY_NAME} source, @MappingTarget ${ENTITY_NAME} target);
    }
}
