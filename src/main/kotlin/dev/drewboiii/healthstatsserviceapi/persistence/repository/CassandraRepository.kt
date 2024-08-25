package dev.drewboiii.healthstatsserviceapi.persistence.repository

import dev.drewboiii.healthstatsserviceapi.persistence.model.Countries
import dev.drewboiii.healthstatsserviceapi.persistence.model.DayStatistics
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.data.cassandra.repository.CassandraRepository
import java.time.LocalDate
import java.util.*

@ConditionalOnProperty(name = ["application.cassandra.enabled"], havingValue = "true", matchIfMissing = true)
interface DayStatisticsCassandraRepository : CassandraRepository<DayStatistics, UUID> {

    fun findDayStatsByProviderAndCountryInAndReqDate(providerName: String, countryName: List<String>, reqDate: LocalDate): List<DayStatistics>

}

@ConditionalOnProperty(name = ["application.cassandra.enabled"], havingValue = "true", matchIfMissing = true)
interface CountriesCassandraRepository : CassandraRepository<Countries, String> {

    fun findByProvider(providerName: String): Countries?

}
