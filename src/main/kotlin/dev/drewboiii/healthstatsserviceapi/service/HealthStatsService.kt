package dev.drewboiii.healthstatsserviceapi.service

import dev.drewboiii.healthstatsserviceapi.dto.HealthServiceAvailableCountriesResponse
import dev.drewboiii.healthstatsserviceapi.dto.HealthServiceTodayStatsResponse
import dev.drewboiii.healthstatsserviceapi.provider.HealthStatsProvider
import org.springframework.stereotype.Service

@Service
class HealthStatsService(
    private val provider: Map<String, HealthStatsProvider>
) {

    fun getTodayStats(country: String, providerName: String): HealthServiceTodayStatsResponse {
        val statsProvider = provider[providerName] ?: throw RuntimeException("Unknown provider") // TODO: Exception class

        val isCorrectCountry = statsProvider.getAvailableCountries().map(String::lowercase).contains(country.lowercase())

        if (!isCorrectCountry) {
            throw RuntimeException("Incorrect country") // TODO: Exception class
        }

        return statsProvider.getTodayStats(country)
    }

    fun getAvailableCountries(providerName: String): HealthServiceAvailableCountriesResponse {
        val statsProvider = provider[providerName] ?: throw RuntimeException("Unknown provider") // TODO: Exception class

        // TODO: add paging
        return HealthServiceAvailableCountriesResponse(
            countries = statsProvider.getAvailableCountries()
        )
    }

}