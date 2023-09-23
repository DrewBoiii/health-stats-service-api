package dev.drewboiii.healthstatsserviceapi.service

import dev.drewboiii.healthstatsserviceapi.dto.HistoryStatisticsByDay
import dev.drewboiii.healthstatsserviceapi.dto.toHistoryStatisticsByDay
import dev.drewboiii.healthstatsserviceapi.exception.CountryNotSupportedException
import dev.drewboiii.healthstatsserviceapi.exception.NotFoundException
import dev.drewboiii.healthstatsserviceapi.exception.UnknownProviderException
import dev.drewboiii.healthstatsserviceapi.persistence.DayStatistics
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class HistoryHealthStatsService(
    private val cassandraService: CassandraService,
    private val healthStatsService: HealthStatsService
) {
    fun getStatsByDay(provider: String, country: String, day: LocalDate): List<HistoryStatisticsByDay> {
        if (!healthStatsService.getAvailableProviders().contains(provider)) {
            throw UnknownProviderException(provider)
        }

        val requestedCountries = country.split(",").filterNot { it.isEmpty() }

        for (requestedCountry in requestedCountries) {
            if (cassandraService.getCountries(provider)?.countries?.contains(requestedCountry) == false) {
                throw CountryNotSupportedException(provider, requestedCountry)
            }
        }

        val statsByDay: List<DayStatistics> = cassandraService.getStatsByDay(provider, requestedCountries, day)

        if (statsByDay.isEmpty()) {
            throw NotFoundException("Stats were not found")
        }

        return statsByDay.map { it.toHistoryStatisticsByDay() }
    }


}