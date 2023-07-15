package dev.drewboiii.healthstatsserviceapi.config

import dev.drewboiii.healthstatsserviceapi.HealthStatsServiceApiApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients(basePackageClasses = [HealthStatsServiceApiApplication::class])
class ApplicationConfig