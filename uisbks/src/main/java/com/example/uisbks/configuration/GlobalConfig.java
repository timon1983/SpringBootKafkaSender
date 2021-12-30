package com.example.uisbks.configuration;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.time.Duration;

/**
 * Конфигурация для настройки рабочего каталога при работе с jsp
 */
@Configuration
@RequiredArgsConstructor
@Setter
public class GlobalConfig {

    private String token;

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> customizer() {
        return factory -> factory.addContextCustomizers(context -> {
                    String relativePath = "uisbks/src/main/webapp";
                    File docBaseFile = new File(relativePath);
                    if (docBaseFile.exists()) {
                        context.setDocBase(docBaseFile.getAbsolutePath());
                    }
                }
        );
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofMillis(60000))
                .setReadTimeout(Duration.ofMillis(60000))
                .build();
    }

    @Bean("token")
    public String getToken(){
        return token;
    }
}