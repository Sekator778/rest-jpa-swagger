package com.example.bookjpa.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class MvcConfigurer implements WebMvcConfigurer {
    private final Logger log = LoggerFactory.getLogger(MvcConfigurer.class);
    private final BuildProperties buildProperties;

    public MvcConfigurer(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    OpenAPI customOpenAPI() {
        log.info("customOpenAPI has created");
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title(buildProperties.getArtifact() + " - API by mentor SHPP")
                        .version(buildProperties.getVersion())
                        .description(buildProperties.getArtifact() + " - API Swagger documentation")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new CookieLocaleResolver();
    }

    @Bean
    public LocaleChangeInterceptor localeInterceptor() {
        LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
        localeInterceptor.setParamName("lang");
        return localeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeInterceptor());
    }
}