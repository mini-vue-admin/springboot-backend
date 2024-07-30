package i.g.sbl.sky.basic.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.SneakyThrows;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.format.DateTimeFormatter;

public class JsonUtils {
    private static final String dateFormat = "yyyy-MM-dd";
    private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";

    private static final ObjectMapper mapper;

    static {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.simpleDateFormat(dateTimeFormat);
        builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)));
        builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)));
        mapper = builder.build();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


    @SneakyThrows
    public static String toJson(Object obj) {
        if (obj == null) {
            return null;
        }
        return mapper.writeValueAsString(obj);
    }

    @SneakyThrows
    public static <T> T fromJson(String json, Class<T> clz) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        return mapper.readValue(json, clz);
    }
}
