package dev.drewboiii.healthstatsserviceapi.dto

import com.fasterxml.jackson.annotation.JsonAlias

open class Covid19BaseResponse(
    open val get: String,
    open val results: Int
)

data class Covid19CountriesResponse(
    val response: Set<String>,
    val parameters: Any,
    override val get: String,
    override val results: Int,
    val errors: List<Any>
) : Covid19BaseResponse(get, results)

data class Covid19StatisticsResponse(
    val response: List<Stats>,
    val parameters: Parameters,
    override val get: String,
    override val results: Int,
    val errors: List<Any>
) : Covid19BaseResponse(get, results) {
    data class Stats(
        val continent: String,
        val country: String,
        val population: Long,
        val cases: Cases,
        val deaths: Deaths,
        val tests: Tests,
        val day: String,
        val time: String
    ) {
        data class Cases(
            val new: String?,
            val active: Long,
            val critical: Long?,
            val recovered: Long,
            @field:JsonAlias("1M_pop")
            val oneMillionPopulation: String,
            val total: Long
        )

        data class Deaths(
            val new: String,
            @field:JsonAlias("1M_pop")
            val oneMillionPopulation: String,
            val total: Long
        )

        data class Tests(
            @field:JsonAlias("1M_pop")
            val oneMillionPopulation: String,
            val total: Long
        )
    }

    data class Parameters(
        val country: String
    )

}

fun Covid19StatisticsResponse.Stats.toDto(country: String): HealthServiceTodayStatsResponse =
    HealthServiceTodayStatsResponse(
        country = country,
        continent = this.continent,
        newCases = this.cases.new,
        criticalCases = this.cases.critical,
        totalInfected = this.cases.total,
        newDeaths = this.deaths.new,
        totalDeaths = this.deaths.total,
        vaccinated = this.tests.total
    )
