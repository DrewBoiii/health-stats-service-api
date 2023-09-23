package dev.drewboiii.healthstatsserviceapi.controller

import dev.drewboiii.healthstatsserviceapi.dto.HistoryStatisticsByDay
import dev.drewboiii.healthstatsserviceapi.service.HistoryHealthStatsService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/stats")
class HealthStatsHistoryController(
    private val historyHealthStatsService: HistoryHealthStatsService
) {

    @GetMapping("/history")
    fun getStatsByDay(
        @RequestParam country: String,
        @RequestParam provider: String,
        @RequestParam day: LocalDate
    ): List<HistoryStatisticsByDay> = historyHealthStatsService.getStatsByDay(provider, country, day)

}