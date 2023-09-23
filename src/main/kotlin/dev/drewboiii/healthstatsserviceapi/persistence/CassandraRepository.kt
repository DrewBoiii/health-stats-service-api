package dev.drewboiii.healthstatsserviceapi.persistence

import org.springframework.data.cassandra.repository.CassandraRepository
import java.time.LocalDate
import java.util.*

interface DayStatisticsCassandraRepository : CassandraRepository<DayStatistics, UUID> {

    fun findDayStatsByProviderAndCountryInAndReqDate(providerName: String, countryName: List<String>, reqDate: LocalDate): List<DayStatistics>

}

interface CountriesCassandraRepository : CassandraRepository<Countries, String> {

    fun findByProvider(providerName: String): Countries?

}
