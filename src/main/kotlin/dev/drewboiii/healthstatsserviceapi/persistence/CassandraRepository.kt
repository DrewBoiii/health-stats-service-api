package dev.drewboiii.healthstatsserviceapi.persistence

import org.springframework.data.cassandra.repository.CassandraRepository
import java.util.*

interface DayStatisticsCassandraRepository : CassandraRepository<DayStatistics, UUID> {

    fun findDayStatsByProviderAndCountry(providerName: String, countryName: String): DayStatistics?

}

interface CountriesCassandraRepository : CassandraRepository<Countries, String> {

    fun findByProvider(providerName: String): Countries?

}
