package dev.drewboiii.healthstatsserviceapi.config

import dev.drewboiii.healthstatsserviceapi.client.Covid19ClientHeaderRequestInterceptor
import feign.Logger
import feign.RequestInterceptor
import feign.Retryer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import java.util.concurrent.TimeUnit

class Covid19FeignConfig(
    @Value("\${feign.client.config.covid.api.retry-max-attempts:5}")
    private val retryMaxAttempts: Int
) : FeignConfig() {

    @Bean
    fun requestInterceptor(): RequestInterceptor = Covid19ClientHeaderRequestInterceptor()

    @Bean
    fun feignLoggerLevel(): Logger.Level = Logger.Level.FULL

    @Bean
    fun retryer(): Retryer = Retryer.Default(100L, TimeUnit.SECONDS.toMillis(3L), retryMaxAttempts)

}