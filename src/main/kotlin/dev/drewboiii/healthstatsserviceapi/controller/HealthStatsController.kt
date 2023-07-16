package dev.drewboiii.healthstatsserviceapi.controller

import dev.drewboiii.healthstatsserviceapi.dto.HealthServiceAvailableCountriesResponse
import dev.drewboiii.healthstatsserviceapi.dto.HealthServiceTodayStatsResponse
import dev.drewboiii.healthstatsserviceapi.service.HealthStatsService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/stats")
class HealthStatsController(
    private val healthStatsService: HealthStatsService
) {

    @GetMapping("/today")
    fun get(@RequestParam country: String, @RequestParam provider: String): HealthServiceTodayStatsResponse =
        healthStatsService.getTodayStats(country, provider)

    @GetMapping("/countries")
    fun get(@RequestParam provider: String): HealthServiceAvailableCountriesResponse =
        healthStatsService.getAvailableCountries(provider)

}