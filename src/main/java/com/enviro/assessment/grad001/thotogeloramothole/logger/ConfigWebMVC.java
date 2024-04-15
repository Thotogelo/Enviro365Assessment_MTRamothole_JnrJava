package com.enviro.assessment.grad001.thotogeloramothole.logger;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConfigWebMVC implements WebMvcConfigurer {

//    Registers the LoggingInterceptor class as an interceptor
//    The LoggingInterceptor class logs information about the request and response
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggingInterceptor());
    }
}
