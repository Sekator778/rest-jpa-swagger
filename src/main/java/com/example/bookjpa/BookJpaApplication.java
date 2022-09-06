package com.example.bookjpa;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources({
        @PropertySource(value = "file:/app.properties", ignoreResourceNotFound = true)
})
@RequiredArgsConstructor
public class BookJpaApplication {
    private static final Logger log = LoggerFactory.getLogger(BookJpaApplication.class);

    public static void main(String[] args) {
        log.info("main init");
        SpringApplication.run(BookJpaApplication.class, args);
        log.info("main done");
    }
}
