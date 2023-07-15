package dev.drewboiii.healthstatsserviceapi.provider.impl

import dev.drewboiii.healthstatsserviceapi.client.Covid19Client
import dev.drewboiii.healthstatsserviceapi.dto.HealthServiceTodayStatsResponse
import dev.drewboiii.healthstatsserviceapi.provider.HealthStatsProvider
import org.springframework.stereotype.Component

@Component("COVID19")
class Covid19StatsProvider(val client: Covid19Client) : HealthStatsProvider {

    override fun getTodayStats(country: String): HealthServiceTodayStatsResponse {
        val statisticsResponse = client.getStatistics(country)
        val response = statisticsResponse.response
        val stats = response.firstOrNull()

        return HealthServiceTodayStatsResponse(
            country = country,
            continent = stats?.continent,
            newCases = stats?.cases?.new,
            criticalCases = stats?.cases?.critical,
            totalInfected = stats?.cases?.total,
            newDeaths = stats?.deaths?.new,
            totalDeaths = stats?.deaths?.total,
            vaccinated = stats?.tests?.total
        )
    }

    override fun getAvailableCountries(): Set<String> {
        // TODO: get from db
        return setOf("Russia", "USA")
    }
}