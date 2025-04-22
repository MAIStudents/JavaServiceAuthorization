package ru.mai.lessons.rpks.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Сервис BFF")
                        .description("Сервис по общению с manager_service с токенизированной аутентификацией")
                        .version("1.0"));
    }
}