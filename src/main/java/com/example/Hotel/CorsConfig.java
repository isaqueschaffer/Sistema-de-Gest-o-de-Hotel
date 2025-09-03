package com.example.Hotel;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // libera todos os endpoints da API
                        .allowedOrigins("http://127.0.0.1:5500") // libera o front-end que roda nesse endereço
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // métodos liberados
                        .allowedHeaders("*"); // todos os headers
            }
        };
    }
}
