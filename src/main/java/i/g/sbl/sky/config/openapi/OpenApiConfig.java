package i.g.sbl.sky.config.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class OpenApiConfig {
    static {
        var schema = new Schema<LocalDateTime>();
        schema.example(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).type("string");
        SpringDocUtils.getConfig().replaceWithSchema(LocalDateTime.class, schema);
    }
    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info().title("SkyProbe").version("v1"));

    }
}
