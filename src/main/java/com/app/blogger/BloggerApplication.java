package com.app.blogger;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

@ServletComponentScan
@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Blogger app REST APIs",
                description = "Blogger app REST API Documentation",
                version = "v1.0",
                contact = @Contact(
                        name = "Soumya Ghosh",
                        email = "soumya.ghosh71@gmail.com"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "Blogger app Documentation",
                url = "https://github.com/soumyaghosh71/Blogger"
        )
)
public class BloggerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BloggerApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
