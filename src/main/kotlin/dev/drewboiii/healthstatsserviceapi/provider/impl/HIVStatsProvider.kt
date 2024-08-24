package dev.drewboiii.healthstatsserviceapi.provider.impl

import dev.drewboiii.healthstatsserviceapi.client.HivClient
import dev.drewboiii.healthstatsserviceapi.dto.HealthServiceTodayStatsResponse
import dev.drewboiii.healthstatsserviceapi.dto.HivStatisticsTodayResponse
import dev.drewboiii.healthstatsserviceapi.dto.toHealthServiceTodayStatsResponse
import dev.drewboiii.healthstatsserviceapi.provider.HealthStatsProvider
import dev.drewboiii.healthstatsserviceapi.provider.HealthStatsProviderType
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Order(2)
@Component("HIV")
class HIVStatsProvider(
    private val hivClient: HivClient
) : HealthStatsProvider {

    override fun getTodayStats(country: String): HealthServiceTodayStatsResponse {
        val (statistics) = hivClient.getTodayStatistics("some-api-key", country)

        val todayStats = if (statistics.isNotEmpty()) statistics.firstOrNull() else throw RuntimeException("No data")

        return todayStats?.toHealthServiceTodayStatsResponse() ?: throw RuntimeException("No stats")
    }

    override fun getAvailableCountries(): List<String> {
        val (countries, quantity) = hivClient.getCountries("some-api-key")

        return if (quantity > 0) countries.toList() else emptyList()
    }

    override fun getProviderName(): String = HealthStatsProviderType.HIV.name

}