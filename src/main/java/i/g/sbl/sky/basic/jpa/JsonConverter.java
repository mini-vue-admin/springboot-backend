package i.g.sbl.sky.basic.jpa;

import i.g.sbl.sky.basic.utils.JsonUtils;
import jakarta.persistence.AttributeConverter;
import lombok.Data;

@Data
public class JsonConverter<T> implements AttributeConverter<T, String> {
    private Class<T> clazz;

    public JsonConverter(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public String convertToDatabaseColumn(T t) {
        return JsonUtils.toJson(t);
    }

    @Override
    public T convertToEntityAttribute(String s) {
        return JsonUtils.fromJson(s, clazz);
    }
}
