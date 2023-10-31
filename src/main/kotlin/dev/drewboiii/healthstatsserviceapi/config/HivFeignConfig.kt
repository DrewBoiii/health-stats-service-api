package dev.drewboiii.healthstatsserviceapi.config

import feign.Logger
import feign.Retryer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import java.util.concurrent.TimeUnit

class HivFeignConfig(
    @Value("\${feign.client.config.hiv.api.retry-max-attempts:5}")
    private val retryMaxAttempts: Int
): FeignConfig() {

    @Bean
    fun feignLoggerLevel(): Logger.Level = Logger.Level.FULL

    @Bean
    fun retryer(): Retryer = Retryer.Default(100L, TimeUnit.SECONDS.toMillis(10L), retryMaxAttempts)

}