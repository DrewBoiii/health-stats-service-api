package dev.drewboiii.healthstatsserviceapi.service

import dev.drewboiii.healthstatsserviceapi.dto.HistoryStatisticsByDay
import dev.drewboiii.healthstatsserviceapi.dto.ProvidersResponse
import dev.drewboiii.healthstatsserviceapi.dto.toHistoryStatisticsByDay
import dev.drewboiii.healthstatsserviceapi.dto.toProvidersResponse
import dev.drewboiii.healthstatsserviceapi.exception.UnknownProviderException
import mu.KLogging
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class HistoryHealthStatsService(
    private val cassandraService: CassandraService?,
    private val mongoService: MongoService?,
    private val healthStatsService: HealthStatsService
) {
//    fun getStatsByDay(provider: String, country: String, day: LocalDate): List<HistoryStatisticsByDay> {
//        if (!healthStatsService.getAvailableProviders().contains(provider)) {
//            throw UnknownProviderException(provider)
//        }
//
//        val requestedCountries = country.split(",").filterNot { it.isEmpty() }
//
//        for (requestedCountry in requestedCountries) {
//            if (cassandraService?.getCountries(provider)?.countries?.contains(requestedCountry) == false) {
//                throw CountryNotSupportedException(provider, requestedCountry)
//            }
//        }
//
//        val statsByDay: List<DayStatistics> =
//            cassandraService?.getStatsByDay(provider, requestedCountries, day) ?: emptyList()
//
//        if (statsByDay.isEmpty()) {
//            throw NotFoundException("Stats from Cassandra were not found")
//        }
//
//        return statsByDay.map { it.toHistoryStatisticsByDay() }
//    }

    //todo common repository for saving (mongo, cassandra, etc.)

    fun getStatsByDay(provider: String, country: String, day: LocalDate): List<HistoryStatisticsByDay> {
        if (!healthStatsService.getAvailableProviders().contains(provider)) {
            throw UnknownProviderException(provider)
        }

        var requestedCountries: List<String>? = null
        if ("*" != country) {
            requestedCountries = country.split(",").filterNot { it.isEmpty() }
        }

        val statsByDay = mongoService?.getStatsByDay(provider, requestedCountries, day) ?: emptyList()

        if (statsByDay.isEmpty()) {
            logger.warn { "Stats from Mongo were not found" }
        }

        return statsByDay.map { it.toHistoryStatisticsByDay() }
    }

    fun getProviders(): ProvidersResponse =
        mongoService?.getProviders()?.toProvidersResponse() ?: ProvidersResponse(providers = emptyList())

    companion object : KLogging()
}