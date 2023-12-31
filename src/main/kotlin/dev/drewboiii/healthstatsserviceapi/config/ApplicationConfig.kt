package dev.drewboiii.healthstatsserviceapi.config

import dev.drewboiii.healthstatsserviceapi.HealthStatsServiceApiApplication
import dev.drewboiii.healthstatsserviceapi.config.properties.CachePropertiesMap
import dev.drewboiii.healthstatsserviceapi.config.properties.AppKafkaPropertiesMap
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients(basePackageClasses = [HealthStatsServiceApiApplication::class])
@EnableConfigurationProperties(*[CachePropertiesMap::class, AppKafkaPropertiesMap::class])
class ApplicationConfig