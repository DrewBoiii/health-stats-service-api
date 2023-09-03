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
    @PrimaryKeyColumn(name = "id", ordinal = 0, type = PrimaryKeyType.PARTITIONED) private val id: UUID,
    @field:Column("req_date") private val reqDate: LocalDate,
    private val provider: HealthStatsProviderType,
    private val country: String,
    private val continent: String?,
    @field:Column("new_cases") private val newCases: String?,
    @field:Column("critical_cases") private val criticalCases: Long?,
    @field:Column("total_infected") private val totalInfected: Long?,
    @field:Column("new_deaths") private val newDeaths: String?,
    @field:Column("total_deaths") private val totalDeaths: Long?,
    private val vaccinated: Long?
)

@Table("available_countries")
data class AvailableCountries(
    @PrimaryKeyColumn(name = "provider", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private val provider: HealthStatsProviderType,
    private val countries: List<String>
)