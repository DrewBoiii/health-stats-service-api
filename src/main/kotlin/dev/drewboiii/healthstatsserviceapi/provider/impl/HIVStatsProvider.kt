package dev.drewboiii.healthstatsserviceapi.provider.impl

import dev.drewboiii.healthstatsserviceapi.dto.HealthServiceTodayStatsResponse
import dev.drewboiii.healthstatsserviceapi.exception.NotImplementedException
import dev.drewboiii.healthstatsserviceapi.provider.HealthStatsProvider
import org.springframework.stereotype.Component

@Component("HIV")
class HIVStatsProvider : HealthStatsProvider {

    override fun getTodayStats(country: String): HealthServiceTodayStatsResponse {
        throw NotImplementedException()
    }

    override fun getAvailableCountries(): Set<String> {
        throw NotImplementedException()
    }
}