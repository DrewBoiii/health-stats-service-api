package dev.drewboiii.healthstatsserviceapi.provider.impl

import dev.drewboiii.healthstatsserviceapi.dto.HealthServiceTodayStatsResponse
import dev.drewboiii.healthstatsserviceapi.exception.NotImplementedException
import dev.drewboiii.healthstatsserviceapi.provider.HealthStatsProvider
import dev.drewboiii.healthstatsserviceapi.provider.HealthStatsProviderType
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Order(3)
@Component("CANCER")
class CancerStatsProvider : HealthStatsProvider {

    override fun getTodayStats(country: String): HealthServiceTodayStatsResponse {
        throw NotImplementedException()
    }

    override fun getAvailableCountries(): List<String> {
        throw NotImplementedException()
    }

    override fun getProviderName(): String = HealthStatsProviderType.CANCER.name
}