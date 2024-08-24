package dev.drewboiii.healthstatsserviceapi.persistence

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

@ConditionalOnProperty(name = ["application.mongo.enabled"], havingValue = "true", matchIfMissing = true)
interface DayStatisticsMongoRepository : MongoRepository<DayStatisticsEntity, String> {

    @Query("{country:'?0'}")
    fun findByCountry(country: String): DayStatisticsEntity?

}

@ConditionalOnProperty(name = ["application.mongo.enabled"], havingValue = "true", matchIfMissing = true)
interface ProvidersMongoRepository : MongoRepository<ProviderEntity, String>