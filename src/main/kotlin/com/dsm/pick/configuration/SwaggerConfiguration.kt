package com.dsm.pick.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

@Configuration
class SwaggerConfiguration {

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .useDefaultResponseMessages(false)
            .consumes(getConsumeContentTypes())
            .produces(getProduceContentTypes())
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.freewheelin.push.controller"))
            .paths(PathSelectors.any())
            .build()
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfo(
            "Freewheelin API Docs",
            "Freewheelin Company APIs",
            "1.0.0",
            "Terms of Service URL",
            "Contact Name",
            "License",
            "License URL",
        )
    }
    private fun getConsumeContentTypes() = setOf("application/json")
    private fun getProduceContentTypes() = setOf("application/json")
}