package com.reson8.app.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * By default, Jackson is too lenient with unknown fields being passed in the request bodies for the controllers, so this class contains an
 * ObjectMapper which makes the validation more strict, and adds a JavaTimeModule so LocalDate objects can also be validated in the entities
 * using @NotNull.
 */
@Configuration
public class JacksonConfig {
  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
    objectMapper.registerModule(new JavaTimeModule());
    return objectMapper;
  }
}