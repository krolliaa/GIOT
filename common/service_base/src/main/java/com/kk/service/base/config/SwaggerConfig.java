package com.kk.service.base.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket webApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .apiInfo(webApiInfo()) //文档介绍
                .select() //选择路径的路由器
                .paths(Predicates.and(PathSelectors.regex("/api/.*"))) //包含哪些路径
                .build();
    }

    @Bean
    public Docket adminApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("adminApi")
                .apiInfo(adminApiInfo()) //文档介绍
                .select()
                .paths(Predicates.and(PathSelectors.regex("/admin/.*")))
                .build();
    }

    public ApiInfo webApiInfo() {
        return new ApiInfoBuilder()
                .title("前端 API 接口文档")
                .description("本文档描述了该项目前端接口相关定义")
                .version("1.0")
                .contact(new Contact("kk", "https://google.com", "888888888@gmail.com"))
                .build();
    }

    public ApiInfo adminApiInfo() {
        return new ApiInfoBuilder()
                .title("后端 API 接口文档")
                .description("本文档描述了该项目后端接口相关定义")
                .version("1.0")
                .contact(new Contact("kk", "https://google.com", "888888888@gmail.com"))
                .build();
    }
}