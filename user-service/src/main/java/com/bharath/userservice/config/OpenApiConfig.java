package com.bharath.userservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI userServiceApiDocs(){
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("User service API")
                        .description("User service API for HOME ENERGY TRACKER")
                        .contact(getContact())
                        .version("1.0.1")
                );
    }

    private static Contact getContact(){
        Contact contact = new Contact();
        contact.setEmail("bharathravelli419@gmail.com");
        contact.setName("Bharath Ravelli");
        return contact;
    }
}
