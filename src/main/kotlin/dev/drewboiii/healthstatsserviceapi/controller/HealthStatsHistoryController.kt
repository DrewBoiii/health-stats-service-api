package dev.drewboiii.healthstatsserviceapi.controller

import dev.drewboiii.healthstatsserviceapi.dto.HistoryStatisticsByDay
import dev.drewboiii.healthstatsserviceapi.dto.ProvidersResponse
import dev.drewboiii.healthstatsserviceapi.service.HistoryHealthStatsService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/stats/history")
class HealthStatsHistoryController(
    private val historyHealthStatsService: HistoryHealthStatsService
) {

    @GetMapping
    fun getStatsByDay(
        @RequestParam country: String,
        @RequestParam provider: String,
        @RequestParam day: LocalDate
    ): List<HistoryStatisticsByDay> = historyHealthStatsService.getStatsByDay(provider, country, day)

    @GetMapping("/providers")
    fun getProviders(): ProvidersResponse = historyHealthStatsService.getProviders()

    @GetMapping("/providers/{provider}")
    fun getProviderCountries(@PathVariable provider: String) = historyHealthStatsService.getProviderCountries(provider)

}