package dev.drewboiii.healthstatsserviceapi.service

import dev.drewboiii.healthstatsserviceapi.dto.HealthServiceTodayStatsResponse
import dev.drewboiii.healthstatsserviceapi.provider.HealthStatsProvider
import org.springframework.stereotype.Component

@Component
class HealthStatsService(
    private val provider: Map<String, HealthStatsProvider>
) {

    fun getTodayStats(country: String, providerName: String): HealthServiceTodayStatsResponse? {
        val statsProvider = provider[providerName] ?: throw RuntimeException("Unknown provider")

        val isCorrectCountry = statsProvider.getAvailableCountries().contains(country.lowercase())

        if (!isCorrectCountry) {
            throw RuntimeException("Incorrect country") // TODO: Exception class
        }

        return statsProvider.getTodayStats(country)
    }

}