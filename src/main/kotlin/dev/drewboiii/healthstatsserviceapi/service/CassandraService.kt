package dev.drewboiii.healthstatsserviceapi.service

import dev.drewboiii.healthstatsserviceapi.dto.HealthServiceTodayStatsResponse
import dev.drewboiii.healthstatsserviceapi.dto.toCassandraModel
import dev.drewboiii.healthstatsserviceapi.persistence.CountriesCassandraRepository
import dev.drewboiii.healthstatsserviceapi.persistence.Countries
import dev.drewboiii.healthstatsserviceapi.persistence.DayStatistics
import dev.drewboiii.healthstatsserviceapi.persistence.DayStatisticsCassandraRepository
import dev.drewboiii.healthstatsserviceapi.provider.HealthStatsProviderType
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
@ConditionalOnProperty(name = ["application.cassandra.enabled"], havingValue = "true", matchIfMissing = true)
class CassandraService(
    private val dayStatsRepository: DayStatisticsCassandraRepository,
    private val countriesRepository: CountriesCassandraRepository
) {

    fun saveDayStats(providerName: String, todayStats: HealthServiceTodayStatsResponse) =
        dayStatsRepository.save(todayStats.toCassandraModel(providerName))

    fun saveAvailableCountries(provider: HealthStatsProviderType, countries: List<String>) =
        countriesRepository.save(Countries(provider, countries))

    fun getCountries(providerName: String) = countriesRepository.findByProvider(providerName)

    fun getStatsByDay(providerName: String, countryName: List<String>, reqDate: LocalDate) : List<DayStatistics> =
        dayStatsRepository.findDayStatsByProviderAndCountryInAndReqDate(providerName, countryName, reqDate)

}