package com.tqi.kotlinbackenddeveloper2023.domain.configuration

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.models.OpenAPI
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
@OpenAPIDefinition(info = Info(title = "Api JuMarket", version = "1.0", description = "API para avaliação TQI Back End developer by Paulo Felipe"))
class Swagger3Config {

    @Bean
    fun publicApi(): GroupedOpenApi? {
        return GroupedOpenApi.builder()
            .group("kotlin-backend-developer-2023")
            .pathsToMatch("/product/**", "/cart/**", "/sale/**")
            .build()
    }

}