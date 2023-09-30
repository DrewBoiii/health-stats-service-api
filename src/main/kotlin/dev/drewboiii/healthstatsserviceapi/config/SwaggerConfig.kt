package dev.drewboiii.healthstatsserviceapi.config

import org.springdoc.core.properties.SpringDocConfigProperties
import org.springdoc.core.properties.SwaggerUiConfigProperties
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnProperty(name = ["springdoc.swagger-ui.enabled"], havingValue = "true", matchIfMissing = true)
class SwaggerConfig(
    private val springDocConfigProperties: SpringDocConfigProperties,
    private val swaggerUiConfigProperties: SwaggerUiConfigProperties
) {

    fun isSwaggerUIEnabled() = swaggerUiConfigProperties.isEnabled

}