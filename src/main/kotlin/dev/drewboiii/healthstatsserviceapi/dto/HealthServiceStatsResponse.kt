package dev.drewboiii.healthstatsserviceapi.dto

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDate

data class HealthServiceTodayStatsResponse(
    val country: String,
    @field:JsonInclude(JsonInclude.Include.NON_NULL)
    val continent: String? = null,
    val newCases: String?,
    @field:JsonInclude(JsonInclude.Include.NON_NULL)
    val criticalCases: Long? = null,
    val totalInfected: Long?,
    val newDeaths: String?,
    val totalDeaths: Long?,
    val vaccinated: Long?,
    val day: LocalDate = LocalDate.now()
)

data class HealthServiceAvailableCountriesResponse(
    val countries: Set<String>
)

data class HealthServiceAvailableProviders(
    val providers: Set<String>
)

