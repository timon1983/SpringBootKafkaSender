package com.example.uisbks.configuration;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * Конфигурация для настройки рабочего каталога при работе с jsp
 *
 */
@Configuration
public class GlobalConfig {
    //JSP cannot find path configuration under multi-module
    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> customizer() {
        return (factory) -> {
            factory.addContextCustomizers((context) -> {
                        //The relative path of webapp in the module
                        String relativePath = "uisbks/src/main/webapp";
                        File docBaseFile = new File(relativePath);
                        // If the path does not exist, add this path
                        if (docBaseFile.exists()) {
                            context.setDocBase(docBaseFile.getAbsolutePath());
                        }
                    }
            );
        };
    }
}