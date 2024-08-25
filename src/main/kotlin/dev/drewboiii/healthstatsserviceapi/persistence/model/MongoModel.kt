package dev.drewboiii.healthstatsserviceapi.persistence.model

import dev.drewboiii.healthstatsserviceapi.provider.HealthStatsProviderType
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.CompoundIndexes
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document("daystats")
@CompoundIndexes(
    CompoundIndex(def = "{'reqDate': 1, 'country': 1, 'provider': 1}", unique = true)
)
data class DayStatisticsEntity(
    @field:Id
    val id: String,
    @field:Indexed
    val reqDate: LocalDate,
    val provider: HealthStatsProviderType,
    @field:Indexed
    val country: String,
    val continent: String?,
    val newCases: String?,
    val criticalCases: Long?,
    val totalInfected: Long?,
    val newDeaths: String?,
    val totalDeaths: Long?,
    val vaccinated: Long?
)

@Document("providers")
@CompoundIndexes(
    CompoundIndex(def = "{'_id': 1, 'countries.name': 1}", unique = true)
)
data class ProviderEntity(
    @field:[Id Indexed]
    val id: String,
    @field:Indexed(unique = true)
    val name: HealthStatsProviderType,
    val countries: List<CountryEntity>
)

@Document("countries")
data class CountryEntity(
    @field:Id
    val id: String,
    @field:Indexed
    val name: String,
    val continent: String? = null
)