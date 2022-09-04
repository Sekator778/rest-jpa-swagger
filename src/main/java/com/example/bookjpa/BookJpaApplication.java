package com.example.bookjpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources({
        @PropertySource(value = "file:app.properties", ignoreResourceNotFound = true)
})
public class BookJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookJpaApplication.class, args);
    }
}
