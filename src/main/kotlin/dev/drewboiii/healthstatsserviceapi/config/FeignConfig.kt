package dev.drewboiii.healthstatsserviceapi.config

import dev.drewboiii.healthstatsserviceapi.client.FeignErrorDecoder
import feign.codec.ErrorDecoder
import feign.slf4j.Slf4jLogger
import org.springframework.context.annotation.Bean


open class FeignConfig {

    @Bean
    fun logger() = Slf4jLogger()

    @Bean
    fun errorDecoder(): ErrorDecoder = FeignErrorDecoder()

}