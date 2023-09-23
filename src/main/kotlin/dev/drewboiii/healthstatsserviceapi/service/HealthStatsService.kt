package dev.drewboiii.healthstatsserviceapi.service

import dev.drewboiii.healthstatsserviceapi.dto.HealthServiceTodayStatsResponse
import dev.drewboiii.healthstatsserviceapi.exception.CountryNotSupportedException
import dev.drewboiii.healthstatsserviceapi.exception.UnknownProviderException
import dev.drewboiii.healthstatsserviceapi.provider.HealthStatsProvider
import dev.drewboiii.healthstatsserviceapi.provider.HealthStatsProviderType
import org.springframework.stereotype.Service

@Service
class HealthStatsService(
    private val provider: Map<String, HealthStatsProvider>
) {

    fun getTodayStats(country: String, providerName: String): HealthServiceTodayStatsResponse {
        val statsProvider = provider[providerName] ?: throw UnknownProviderException(providerName)

        val isCorrectCountry =
            statsProvider.getAvailableCountries().map(String::lowercase).contains(country.lowercase())

        if (!isCorrectCountry) {
            throw CountryNotSupportedException(providerName, country)
        }

        return statsProvider.getTodayStats(country)
    }

    fun getAvailableCountries(providerName: String): List<String> {
        val statsProvider = provider[providerName] ?: throw UnknownProviderException(providerName)

        return statsProvider.getAvailableCountries()
    }

    fun getAvailableProviders() = HealthStatsProviderType.values().map { it.name }.toSet()

}