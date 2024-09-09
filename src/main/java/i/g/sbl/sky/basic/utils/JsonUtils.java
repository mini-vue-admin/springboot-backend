package i.g.sbl.sky.basic.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.SneakyThrows;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * JsonUtils 类提供了一组静态方法，用于处理 JSON 序列化和反序列化。
 */
public class JsonUtils {
    /**
     * 定义日期格式。
     */
    private static final String dateFormat = "yyyy-MM-dd";
    /**
     * 定义日期时间格式。
     */
    private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";

    /**
     * 定义 ObjectMapper 实例，用于 JSON 处理。
     */
    private static final ObjectMapper mapper;

    static {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        // 设置日期时间格式
        builder.simpleDateFormat(dateTimeFormat);
        // 注册日期序列化器
        builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)));
        builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)));
        // 注册日期反序列化器
        builder.deserializers(new LocalDateDeserializer(DateTimeFormatter.ofPattern(dateFormat)));
        builder.deserializers(new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(dateTimeFormat)));
        mapper = builder.build();
        // 配置 ObjectMapper 忽略未知属性
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 将对象序列化为 JSON 字符串。
     *
     * @param obj 要序列化的对象，如果为 null，则返回 null。
     * @return 序列化后的 JSON 字符串。
     */
    @SneakyThrows
    public static String toJson(Object obj) {
        if (obj == null) {
            return null;
        }
        return mapper.writeValueAsString(obj);
    }

    /**
     * 将对象序列化为格式化的 JSON 字符串。
     *
     * @param obj 要序列化的对象，如果为 null，则返回 null。
     * @return 格式化后的 JSON 字符串。
     */
    @SneakyThrows
    public static String toPrettyJson(Object obj) {
        if (obj == null) {
            return null;
        }
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    }

    /**
     * 将 JSON 字符串反序列化为指定类型的对象。
     *
     * @param json JSON 字符串，如果为 null 或空字符串，则返回 null。
     * @param clz  要反序列化的目标类。
     * @param <T>  泛型类型参数。
     * @return 反序列化后的对象。
     */
    @SneakyThrows
    public static <T> T fromJson(String json, Class<T> clz) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        return mapper.readValue(json, clz);
    }

    /**
     * 将 JSON 字符串反序列化为特定类型的列表。
     *
     * @param json    JSON 字符串，如果为 null 或空字符串，则返回空列表。
     * @param itemClz 列表中元素的类类型。
     * @param <T>     泛型类型参数。
     * @return 反序列化后的列表。
     */
    @SneakyThrows
    public static <T> List<T> fromJsonAsList(String json, Class<T> itemClz) {
        if (json == null || json.isEmpty()) {
            return new ArrayList<>();
        }
        return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, itemClz));
    }

    /**
     * 将 JSON 字符串反序列化为 Map。
     *
     * @param json JSON 字符串。
     * @return 反序列化后的 Map 对象。
     */
    @SneakyThrows
    public static Map<String, Object> fromJsonAsMap(String json) {
        return mapper.readValue(json, new TypeReference<>() {
        });
    }
}
