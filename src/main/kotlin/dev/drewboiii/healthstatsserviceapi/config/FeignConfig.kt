package dev.drewboiii.healthstatsserviceapi.config

import dev.drewboiii.healthstatsserviceapi.client.FeignErrorDecoder
import feign.codec.ErrorDecoder
import mu.KotlinLogging
import org.springframework.context.annotation.Bean


open class FeignConfig {

    @Bean
    fun errorDecoder(): ErrorDecoder = FeignErrorDecoder()

}