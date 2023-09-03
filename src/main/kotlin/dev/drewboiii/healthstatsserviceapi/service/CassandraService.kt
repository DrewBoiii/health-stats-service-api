package dev.drewboiii.healthstatsserviceapi.service

import dev.drewboiii.healthstatsserviceapi.dto.HealthServiceTodayStatsResponse
import dev.drewboiii.healthstatsserviceapi.dto.toCassandraModel
import dev.drewboiii.healthstatsserviceapi.persistence.CountriesCassandraRepository
import dev.drewboiii.healthstatsserviceapi.persistence.Countries
import dev.drewboiii.healthstatsserviceapi.persistence.DayStatisticsCassandraRepository
import dev.drewboiii.healthstatsserviceapi.provider.HealthStatsProviderType
import org.springframework.stereotype.Service

@Service
class CassandraService(
    private val dayStatsRepository: DayStatisticsCassandraRepository,
    private val countriesRepository: CountriesCassandraRepository
) {

    fun saveDayStats(providerName: String, todayStats: HealthServiceTodayStatsResponse) =
        dayStatsRepository.save(todayStats.toCassandraModel(providerName))

    fun saveAvailableCountries(provider: HealthStatsProviderType, countries: List<String>) =
        countriesRepository.save(Countries(provider, countries))

}