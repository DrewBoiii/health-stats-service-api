package dev.drewboiii.healthstatsserviceapi.provider.impl

import dev.drewboiii.healthstatsserviceapi.client.Covid19Client
import dev.drewboiii.healthstatsserviceapi.dto.HealthServiceTodayStatsResponse
import dev.drewboiii.healthstatsserviceapi.dto.toDto
import dev.drewboiii.healthstatsserviceapi.provider.HealthStatsProvider
import org.springframework.stereotype.Component

@Component("COVID19")
class Covid19StatsProvider(val client: Covid19Client) : HealthStatsProvider {

    override fun getTodayStats(country: String): HealthServiceTodayStatsResponse {
        val (response, parameters, get, results, errors) = client.getStatistics(country)

        if (errors.isNotEmpty()) {
            throw RuntimeException("Unknown client error")
        }

        val stats = if (results > 0) response.firstOrNull() else throw RuntimeException("No data")

        return stats?.toDto(country) ?: throw RuntimeException("No stats")
    }

    override fun getAvailableCountries(): Set<String> {
        val (response, parameters, get, results, errors) = client.getCountries()

        if (errors.isNotEmpty()) {
            throw RuntimeException("Unknown client error")
        }

        return if (results > 0) response else emptySet()
    }
}