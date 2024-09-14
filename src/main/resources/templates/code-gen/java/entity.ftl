package ${MODULE_PACKAGE};

<#if EXTENDS_BASE_ENTITY>
import i.g.sbl.sky.basic.jpa.BaseEntity;
</#if>
<#if !EXTENDS_BASE_ENTITY>
import jakarta.persistence.Id;
</#if>
<#if ENTITY_PRIMARY_KEYS?? && (ENTITY_PRIMARY_KEYS?size gt 1)>
import jakarta.persistence.IdClass;
</#if>
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
<#list ENTITY_PACKAGE_IMPORTS as pkg>
import ${pkg};
</#list>

/**
 * ${TABLE_COMMENT}
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "${TABLE_NAME}")
<#if ENTITY_PRIMARY_KEYS?? && (ENTITY_PRIMARY_KEYS?size gt 1)>
@IdClass(${ENTITY_NAME}.${ENTITY_NAME}Id.class)
</#if>
public class ${ENTITY_NAME}<#if EXTENDS_BASE_ENTITY> extends BaseEntity</#if> {
    <#list FIELDS as f>

    /**
     * ${f.FIELD_COMMENT}
     */
    <#if f.PRIMARY_KEY>
    @Id
    </#if>
    private <#if f.IS_ENUM>${f.ENUM_DEFINE.ENUM_NAME}<#else>${f.FIELD_TYPE}</#if> ${f.FIELD_NAME};
    </#list>

    public void copyNonNulls(${ENTITY_NAME} ${ENTITY_FIELD_NAME}) {
        Mapper.INSTANCE.map(${ENTITY_FIELD_NAME}, this);
    }

    @org.mapstruct.Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public interface Mapper {
        Mapper INSTANCE = Mappers.getMapper(${ENTITY_NAME}.Mapper.class);

        void map(${ENTITY_NAME} source, @MappingTarget ${ENTITY_NAME} target);
    }
    <#if ENTITY_PRIMARY_KEYS?? && (ENTITY_PRIMARY_KEYS?size gt 1)>

    @Data
    public static class ${ENTITY_NAME}Id {
    <#list ENTITY_PRIMARY_KEYS as f>

        /**
        * ${f.FIELD_COMMENT}
        */
        private <#if f.IS_ENUM>${f.ENUM_DEFINE.ENUM_NAME}<#else>${f.FIELD_TYPE}</#if> ${f.FIELD_NAME};
    </#list>
    }
    </#if>
}
