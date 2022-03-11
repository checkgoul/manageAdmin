package com.nynu.goule.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class CrossConfig implements WebMvcConfigurer {

    @Resource
    private LoginInterceptor loginInterceptor;

    private final Logger logger = LoggerFactory.getLogger(CrossConfig.class);

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        logger.info("请求接入 --- --- ---");
//        allowedOrigins在新得spring boot中会报错 使用allowedOriginPatterns
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET","HEAD","POST","PUT","DELETE","OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 自定义拦截(防止new LoginInterceptor()无法注入redistemplate 所以让LoginInterceptor注入进入spring容器)
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**");
        // 加入拦截器
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
