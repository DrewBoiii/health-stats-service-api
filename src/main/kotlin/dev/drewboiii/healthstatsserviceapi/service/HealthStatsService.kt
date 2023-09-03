package dev.drewboiii.healthstatsserviceapi.service

import dev.drewboiii.healthstatsserviceapi.dto.HealthServiceTodayStatsResponse
import dev.drewboiii.healthstatsserviceapi.provider.HealthStatsProvider
import dev.drewboiii.healthstatsserviceapi.provider.HealthStatsProviderType
import org.springframework.stereotype.Service

@Service
class HealthStatsService(
    private val provider: Map<String, HealthStatsProvider>,
    private val cassandraService: CassandraService
) {

    fun getTodayStats(country: String, providerName: String): HealthServiceTodayStatsResponse {
        val statsProvider = provider[providerName] ?: throw RuntimeException("Unknown provider") // TODO: Exception class

        val isCorrectCountry =
            statsProvider.getAvailableCountries().map(String::lowercase).contains(country.lowercase())

        if (!isCorrectCountry) {
            throw RuntimeException("Incorrect country") // TODO: Exception class
        }

        val todayStats = statsProvider.getTodayStats(country)

        cassandraService.saveDayStats(providerName, todayStats) // TODO: aspect

        return todayStats
    }

    fun getAvailableCountries(providerName: String): Set<String> {
        val statsProvider =
            provider[providerName] ?: throw RuntimeException("Unknown provider") // TODO: Exception class

        // TODO: add paging
        return statsProvider.getAvailableCountries()
    }

    fun getAvailableProviders() = HealthStatsProviderType.values().map { it.name }.toSet()

}