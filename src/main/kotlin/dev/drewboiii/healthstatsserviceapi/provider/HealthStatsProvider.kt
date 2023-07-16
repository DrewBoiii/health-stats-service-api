package dev.drewboiii.healthstatsserviceapi.provider

import dev.drewboiii.healthstatsserviceapi.dto.HealthServiceTodayStatsResponse
import org.springframework.cache.annotation.Cacheable

interface HealthStatsProvider {

    @Cacheable(cacheNames = ["covid-today-stats-cache"], cacheManager = "MainCacheManager", key = "{#country}")
    fun getTodayStats(country: String): HealthServiceTodayStatsResponse

    @Cacheable(cacheNames = ["covid-countries-cache"], cacheManager = "MainCacheManager")
    fun getAvailableCountries() : Set<String>

}