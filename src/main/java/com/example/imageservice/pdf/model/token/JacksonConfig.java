package com.example.imageservice.pdf.model.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // Register the custom deserializer
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Token.class, new TokenDeserializer());
        objectMapper.registerModule(module);

        return objectMapper;
    }
}
