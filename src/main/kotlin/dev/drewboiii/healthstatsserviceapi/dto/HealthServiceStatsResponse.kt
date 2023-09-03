package dev.drewboiii.healthstatsserviceapi.dto

import com.fasterxml.jackson.annotation.JsonInclude
import dev.drewboiii.healthstatsserviceapi.persistence.DayStatistics
import dev.drewboiii.healthstatsserviceapi.provider.HealthStatsProviderType
import java.time.LocalDate
import java.util.*

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

fun HealthServiceTodayStatsResponse.toCassandraModel(providerName: String) = DayStatistics(
    id = UUID.randomUUID(),
    reqDate = LocalDate.now(),
    provider = HealthStatsProviderType.valueOf(providerName),
    country = this.country,
    continent = this.continent,
    newCases = this.newCases,
    criticalCases = this.criticalCases,
    totalInfected = this.totalInfected,
    newDeaths = this.newDeaths,
    totalDeaths = this.totalDeaths,
    vaccinated = this.vaccinated
)
