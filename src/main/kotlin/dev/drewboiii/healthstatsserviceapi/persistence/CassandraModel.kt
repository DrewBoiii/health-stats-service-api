package dev.drewboiii.healthstatsserviceapi.persistence

import dev.drewboiii.healthstatsserviceapi.provider.HealthStatsProviderType
import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn
import org.springframework.data.cassandra.core.mapping.Table
import java.time.LocalDate
import java.util.*

@Table("day_stats")
data class DayStatistics(
    @PrimaryKeyColumn(name = "id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    val id: UUID,
    @field:Column("req_date") val reqDate: LocalDate,
    val provider: HealthStatsProviderType,
    val country: String,
    val continent: String?,
    @field:Column("new_cases") val newCases: String?,
    @field:Column("critical_cases") val criticalCases: Long?,
    @field:Column("total_infected") val totalInfected: Long?,
    @field:Column("new_deaths") val newDeaths: String?,
    @field:Column("total_deaths") val totalDeaths: Long?,
    val vaccinated: Long?
)

@Table("countries")
data class Countries(
    @PrimaryKeyColumn(name = "provider", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    val provider: HealthStatsProviderType,
    val countries: List<String>
)