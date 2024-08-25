package dev.drewboiii.healthstatsserviceapi.dto

import com.fasterxml.jackson.annotation.JsonInclude
import dev.drewboiii.healthstatsserviceapi.persistence.model.DayStatistics
import dev.drewboiii.healthstatsserviceapi.persistence.model.DayStatisticsEntity
import dev.drewboiii.healthstatsserviceapi.provider.HealthStatsProviderType
import java.time.LocalDate
import java.util.*

data class HealthServiceTodayStatsResponse(
    val country: String,
    @field:JsonInclude(JsonInclude.Include.NON_NULL)
    val continent: String? = null,
    @field:JsonInclude(JsonInclude.Include.NON_NULL)
    val newCases: String? = null,
    @field:JsonInclude(JsonInclude.Include.NON_NULL)
    val criticalCases: Long? = null,
    val totalInfected: Long?,
    @field:JsonInclude(JsonInclude.Include.NON_NULL)
    val newDeaths: String? = null,
    val totalDeaths: Long?,
    val vaccinated: Long?,
    val day: LocalDate = LocalDate.now(),
    val providerName: String
)

data class HealthServiceAvailableCountriesResponse(
    val countries: List<String>
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

fun HealthServiceTodayStatsResponse.toMongoModel(providerName: String) = DayStatisticsEntity(
    id = UUID.randomUUID().toString(),
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
