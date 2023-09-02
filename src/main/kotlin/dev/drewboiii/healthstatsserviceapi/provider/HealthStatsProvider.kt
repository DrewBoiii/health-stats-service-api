package dev.drewboiii.healthstatsserviceapi.provider

import dev.drewboiii.healthstatsserviceapi.dto.HealthServiceTodayStatsResponse

interface HealthStatsProvider {

    fun getTodayStats(country: String): HealthServiceTodayStatsResponse

    fun getAvailableCountries(): Set<String>

}