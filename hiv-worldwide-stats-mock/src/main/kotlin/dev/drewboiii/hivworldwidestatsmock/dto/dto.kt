package dev.drewboiii.hivworldwidestatsmock.dto

import java.time.LocalDateTime

data class StatsResponse(
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
            val lethal: Long? = null
        )

        data class CuredCases(
            val totalRecovered: Long
        )
    }
}

data class CountriesResponse(
    val countries: Set<String>,
    val quantity: Int
)
