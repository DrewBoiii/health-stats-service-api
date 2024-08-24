package dev.drewboiii.healthstatsserviceapi.service

import org.springframework.dao.DuplicateKeyException
import dev.drewboiii.healthstatsserviceapi.dto.HealthServiceTodayStatsResponse
import dev.drewboiii.healthstatsserviceapi.dto.toMongoModel
import dev.drewboiii.healthstatsserviceapi.persistence.CountryEntity
import dev.drewboiii.healthstatsserviceapi.persistence.DayStatisticsMongoRepository
import dev.drewboiii.healthstatsserviceapi.persistence.ProviderEntity
import dev.drewboiii.healthstatsserviceapi.persistence.ProvidersMongoRepository
import dev.drewboiii.healthstatsserviceapi.provider.HealthStatsProviderType
import mu.KLogging
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Service
import java.util.*

@Service
@ConditionalOnProperty(name = ["application.mongo.enabled"], havingValue = "true", matchIfMissing = true)
class MongoService(
    private val dayStatisticsMongoRepository: DayStatisticsMongoRepository,
    private val providersMongoRepository: ProvidersMongoRepository
) : AspectHealthStatsPersistable {

    override fun saveDayStats(providerName: String, todayStats: HealthServiceTodayStatsResponse) {
        try {
        dayStatisticsMongoRepository.save(todayStats.toMongoModel(providerName))
        } catch (ex: DuplicateKeyException) {
            logger.error(ex) { "Duplicate key while saving day statistics to provider $providerName" }
        }
    }

    override fun saveAvailableCountries(provider: HealthStatsProviderType, countries: List<String>) {
        ProviderEntity(
            id = UUID.randomUUID().toString(),
            name = provider,
            countries = countries.map {
                CountryEntity(
                    id = UUID.randomUUID().toString(),
                    name = it
                )
            }
        ).let {
            try {
                providersMongoRepository.save(it)
            } catch (ex: DuplicateKeyException) {
                logger.error(ex) { "Duplicate key while saving available countries to provider ${provider.name}" }
            }
        }
    }

    companion object: KLogging()

}