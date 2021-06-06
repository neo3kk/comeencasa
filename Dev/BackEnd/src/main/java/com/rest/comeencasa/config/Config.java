package com.rest.comeencasa.config;

import com.rest.comeencasa.interceptors.AdminInterceptor;
import com.rest.comeencasa.interceptors.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Config implements WebMvcConfigurer {
    @Autowired
    TokenInterceptor tokenInterceptor;

    @Autowired
    AdminInterceptor adminInterceptor;


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080", "http://localhost:8081")
                .allowedMethods("PUT", "POST", "GET", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .allowedHeaders("*");
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns( "/", "/login", "/register", "/loginOauth", "/auth/oauth2callback/**", "/platos", "/tarifas", "/envios", "/upload/image", "/test", "/images/users/**");
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/admin", "/admin/*");
    }
}
