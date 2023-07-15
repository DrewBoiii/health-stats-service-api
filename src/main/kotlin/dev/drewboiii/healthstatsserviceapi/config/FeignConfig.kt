package dev.drewboiii.healthstatsserviceapi.config

import feign.slf4j.Slf4jLogger
import org.springframework.context.annotation.Bean


open class FeignConfig {

    @Bean
    fun logger() = Slf4jLogger()

}