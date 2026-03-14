package com.priority.gpstracker.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

   @Bean
   public OpenAPI gpsTrackerOpenAPI() {
       return new OpenAPI()
               .info(new Info()
                       .title("GPS Tracker API")
                       .description("A RESTful API for GPS-based location reminder system. This API allows users to create location-based reminders that trigger when they reach a specific GPS coordinate within a defined radius.")
                       .version("1.0.0")
                       .contact(new Contact()
                               .name("GPS Tracker Team")
                               .email("support@gpstracker.com"))
                       .license(new License()
                               .name("MIT License")
                               .url("https://opensource.org/licenses/MIT")))
               .servers(List.of(
                       new Server()
                               .url("http://localhost:5078/api/gps")
                               .description("Development server"),
                       new Server()
                               .url("https://api.gpstracker.com/api/gps")
                               .description("Production server")
               ));
   }
}
