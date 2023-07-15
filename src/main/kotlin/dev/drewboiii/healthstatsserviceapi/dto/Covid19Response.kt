package dev.drewboiii.healthstatsserviceapi.dto

data class Covid19CountriesResponse(
    val get: String,
    val results: Int,
    val errors: List<Any>,
    val response: List<String>
)