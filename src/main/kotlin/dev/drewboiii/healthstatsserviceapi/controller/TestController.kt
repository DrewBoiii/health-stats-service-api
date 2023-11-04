package dev.drewboiii.healthstatsserviceapi.controller

import dev.drewboiii.healthstatsserviceapi.exception.NotImplementedException
import dev.drewboiii.healthstatsserviceapi.provider.HealthStatsProviderType
import dev.drewboiii.healthstatsserviceapi.service.HealthStatsService
import io.github.resilience4j.circuitbreaker.CallNotPermittedException
import mu.KotlinLogging
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger { }

@RestController
@RequestMapping("/test")
class TestController(
    private val healthStatsService: HealthStatsService
) {

    @Transactional
    @GetMapping("/warmup/stats")
    fun saveAllWarmup(): String? {
        var countries: List<String>
        var providerName: String
        for (providerType in HealthStatsProviderType.values()) {
            providerName = providerType.name
            try {
                countries = healthStatsService.getAvailableCountries(providerName)
                for (country in countries) {
                    try {
                        healthStatsService.getTodayStats(country, providerName)
                    } catch (ex: RuntimeException) {
                        logger.error(ex) { "Unknown error, country = $country" }
                        continue
                    }
                }
            } catch (ignore: NotImplementedException) {
            } catch (ex: CallNotPermittedException) {
                logger.error { ex.message }
            }
        }
        return "Success"
    }

}