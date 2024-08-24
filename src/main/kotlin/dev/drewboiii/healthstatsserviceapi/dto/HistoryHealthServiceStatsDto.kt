package dev.drewboiii.healthstatsserviceapi.dto

import dev.drewboiii.healthstatsserviceapi.persistence.DayStatistics
import dev.drewboiii.healthstatsserviceapi.persistence.DayStatisticsEntity
import dev.drewboiii.healthstatsserviceapi.persistence.ProviderEntity
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

data class ProvidersResponse(
    val providers: List<Provider>
) {
    data class Provider(
        val name: String,
        val countries: List<Country>
    ) {
        data class Country(
            val name: String,
            val continent: String? = null
        )
    }
}

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

fun DayStatisticsEntity.toHistoryStatisticsByDay() = HistoryStatisticsByDay(
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

fun List<ProviderEntity>.toProvidersResponse() = ProvidersResponse(
    providers = this.map { provider ->
        ProvidersResponse.Provider(
            name = provider.name.name,
            countries = provider.countries.map { country ->
                ProvidersResponse.Provider.Country(
                    name = country.name,
                    continent = country.continent
                )
            }
        )
    }
)