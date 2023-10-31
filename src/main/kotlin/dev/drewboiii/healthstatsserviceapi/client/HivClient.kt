package dev.drewboiii.healthstatsserviceapi.client

import dev.drewboiii.healthstatsserviceapi.config.HivFeignConfig
import dev.drewboiii.healthstatsserviceapi.dto.HivCountriesResponse
import dev.drewboiii.healthstatsserviceapi.dto.HivStatisticsTodayResponse
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.springframework.cache.annotation.Cacheable
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "hiv", url = "\${feign.client.config.hiv.api.url}", configuration = [HivFeignConfig::class])
interface HivClient {

    @GetMapping("/countries")
    @CircuitBreaker(name = "hiv")
    @Cacheable(cacheNames = ["hiv-countries-cache"], cacheManager = "MainCacheManager")
    fun getCountries(@RequestParam apiKey: String): HivCountriesResponse

    @GetMapping("/today")
    @CircuitBreaker(name = "hiv")
    @Cacheable(cacheNames = ["hiv-today-stats-cache"], cacheManager = "MainCacheManager", key = "{#country}")
    fun getTodayStatistics(@RequestParam apiKey: String, @RequestParam country: String): HivStatisticsTodayResponse

}