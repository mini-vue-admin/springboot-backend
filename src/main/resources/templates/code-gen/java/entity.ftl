package ${MODULE_PACKAGE};

import i.g.sbl.sky.config.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Data
@Entity
@Table(name = "TABLE_NAME")
public class ${ENTITY_NAME} extends BaseEntity {
    <#list FIELDS as f>
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
