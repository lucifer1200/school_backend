package com.org.apiservices.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

//    private final RequestResponseLoggingInterceptor requestResponseLoggingInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(requestResponseLoggingInterceptor);
//    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") //Allow CORS to all implementations
                .allowedOrigins("*") //Allow specific origin
                .allowedMethods("GET", "POST") //Allow specific HTTP methods
                .allowCredentials(false);
    }
}
