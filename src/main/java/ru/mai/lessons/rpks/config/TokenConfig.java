package ru.mai.lessons.rpks.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Data
@Configuration
@ConfigurationProperties(prefix = "token")
public class TokenConfig {

  private String issuer;

  private String signingSecret;

  private Duration expirationTime;
}
