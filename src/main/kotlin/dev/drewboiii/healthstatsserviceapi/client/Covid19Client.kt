package dev.drewboiii.healthstatsserviceapi.client

import dev.drewboiii.healthstatsserviceapi.config.Covid19FeignConfig
import dev.drewboiii.healthstatsserviceapi.dto.Covid19CountriesResponse
import dev.drewboiii.healthstatsserviceapi.dto.Covid19StatisticsResponse
import org.springframework.cache.annotation.Cacheable
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "covid", url = "\${feign.client.config.covid.api.url}", configuration = [Covid19FeignConfig::class])
interface Covid19Client {

    @GetMapping("/countries")
    @Cacheable(cacheNames = ["covid-countries-cache"], cacheManager = "MainCacheManager")
    fun getCountries(): Covid19CountriesResponse

    @GetMapping("/statistics")
    @Cacheable(cacheNames = ["covid-today-stats-cache"], cacheManager = "MainCacheManager", key = "{#country}")
    fun getStatistics(@RequestParam country: String): Covid19StatisticsResponse

}