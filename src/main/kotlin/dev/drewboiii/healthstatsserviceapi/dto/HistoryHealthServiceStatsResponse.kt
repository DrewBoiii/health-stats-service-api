package dev.drewboiii.healthstatsserviceapi.dto

import dev.drewboiii.healthstatsserviceapi.persistence.DayStatistics
import dev.drewboiii.healthstatsserviceapi.provider.HealthStatsProviderType
import java.time.LocalDate

data class HistoryStatisticsByDay(
    private val reqDate: LocalDate,
    val provider: HealthStatsProviderType,
    val country: String,
    val continent: String?,
    val newCases: String?,
    val criticalCases: Long?,
    val totalInfected: Long?,
    val newDeaths: String?,
    val totalDeaths: Long?,
    val vaccinated: Long?
)

fun DayStatistics.toHistoryStatisticsByDay() = HistoryStatisticsByDay(
    reqDate = this.reqDate,
    provider = this.provider,
    country = this.country,
    continent = this.continent,
    newCases = this.newCases,
    criticalCases = this.criticalCases,
    totalInfected = this.totalInfected,
    newDeaths = this.newDeaths,
    totalDeaths = this.totalDeaths,
    vaccinated = this.vaccinated
)