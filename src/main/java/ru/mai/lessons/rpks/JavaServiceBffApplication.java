package ru.mai.lessons.rpks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.mai.lessons.rpks.config.TokenConfig;

@EnableFeignClients
@EnableJpaRepositories
@SpringBootApplication
@EnableCaching
@EnableConfigurationProperties(TokenConfig.class)
public class JavaServiceBffApplication {

  public static void main(String[] args) {
    SpringApplication.run(JavaServiceBffApplication.class, args);
  }
}
