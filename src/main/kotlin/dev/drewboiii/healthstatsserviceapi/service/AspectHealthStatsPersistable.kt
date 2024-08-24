package dev.drewboiii.healthstatsserviceapi.service

import dev.drewboiii.healthstatsserviceapi.dto.HealthServiceTodayStatsResponse
import dev.drewboiii.healthstatsserviceapi.provider.HealthStatsProviderType

interface AspectHealthStatsPersistable {

    fun saveDayStats(providerName: String, todayStats: HealthServiceTodayStatsResponse)

    fun saveAvailableCountries(provider: HealthStatsProviderType, countries: List<String>)

}