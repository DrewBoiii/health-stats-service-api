package dev.drewboiii.healthstatsserviceapi.dto

import dev.drewboiii.healthstatsserviceapi.provider.HealthStatsProviderType
import java.time.LocalDateTime

data class HivStatisticsTodayResponse(
    val statistics: List<Statistics>
)
data class Statistics(
    val country: Country,
    val date: LocalDateTime,
    val cases: Cases,
) {
    data class Country(
        val name: String,
        val continent: String? = null,
        val population: Long? = null
    )

    data class Cases(
        val new: NewCases,
        val dead: DeadCases,
        val cured: CuredCases
    ) {
        data class NewCases(
            val discovered: Long? = null,
            val lethal: Long? = null,
            val active: Long,
            val recovered: Long
        )

        data class DeadCases(
            val lethal: Long? = null,
            val total: Long? = null
        )

        data class CuredCases(
            val totalRecovered: Long
        )
    }
}

data class HivCountriesResponse(
    val countries: Set<String>,
    val quantity: Int
)

fun Statistics.toHealthServiceTodayStatsResponse() = HealthServiceTodayStatsResponse(
    country = this.country.name,
    continent = this.country.continent,
    newCases = this.cases.new.discovered?.toString(),
    criticalCases = this.cases.new.lethal,
    totalInfected = this.cases.new.active,
    newDeaths = this.cases.dead.lethal?.toString(),
    totalDeaths = this.cases.dead.total,
    vaccinated = this.cases.cured.totalRecovered,
    day = this.date.toLocalDate(),
    providerName = HealthStatsProviderType.HIV.name
)
