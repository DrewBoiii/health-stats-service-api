package dev.drewboiii.hivworldwidestatsmock.controller

import dev.drewboiii.hivworldwidestatsmock.dto.CountriesResponse
import dev.drewboiii.hivworldwidestatsmock.dto.StatsResponse
import dev.drewboiii.hivworldwidestatsmock.service.StatsService
import org.jetbrains.annotations.NotNull
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/statistics")
class StatsController(
    private val statsService: StatsService
) {

    @GetMapping("/today")
    fun getTodayStats(@NotNull @RequestParam country: String): StatsResponse = statsService.getTodayStats(country)

    @GetMapping("/countries")
    fun getCountries(): CountriesResponse = statsService.getCountries()

}