package dev.drewboiii.healthstatsserviceapi.controller

import dev.drewboiii.healthstatsserviceapi.provider.HealthStatsProviderType
import dev.drewboiii.healthstatsserviceapi.service.HealthStatsService
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
            countries = healthStatsService.getAvailableCountries(providerName)
            for (country in countries) {
                try {
                    healthStatsService.getTodayStats(country, providerName)
                    Thread.sleep(600)
                } catch (ex: RuntimeException) {
                    logger.error(ex) { "Unknown error, country = $country" }
                    continue
                }
            }
        }
        return "Success"
    }

}