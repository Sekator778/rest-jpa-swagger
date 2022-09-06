//package com.example.bookjpa.configuration;
//
//import io.swagger.v3.oas.models.Components;
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.info.License;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.info.BuildProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.LocaleResolver;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.web.servlet.i18n.CookieLocaleResolver;
//import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
//import org.springframework.web.servlet.i18n.SessionLocaleResolver;
//
//import java.util.Locale;
//
//@Configuration
//public class ConfigurationApplication implements WebMvcConfigurer {
//    private final Logger log = LoggerFactory.getLogger(ConfigurationApplication.class);
//    private final BuildProperties buildProperties;
//
//    @Autowired
//    public ConfigurationApplication(BuildProperties buildProperties) {
//        this.buildProperties = buildProperties;
//    }
//
//    @Bean
//    OpenAPI customOpenAPI() {
//        log.info("customOpenAPI has created");
//        return new OpenAPI()
//                .components(new Components())
//                .info(new Info()
//                        .title(buildProperties.getArtifact() + " - API by mentor SHPP")
//                        .version(buildProperties.getVersion())
//                        .description(buildProperties.getArtifact() + " - API Swagger documentation")
//                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
//    }
//
//    @Bean
//    public LocaleResolver localeResolver() {
//        SessionLocaleResolver slr = new SessionLocaleResolver();
//        slr.setDefaultLocale(Locale.US);
//        log.info("localeResolver set default {}", slr);
//        return slr;
//    }
//
//    @Bean
//    public LocaleChangeInterceptor localeChangeInterceptor() {
//        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
//        lci.setParamName("lang");
//        log.info("localeChangeInterceptor set parameterName as {}", lci.getParamName());
//        return lci;
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(localeChangeInterceptor());
//    }
//
//}
