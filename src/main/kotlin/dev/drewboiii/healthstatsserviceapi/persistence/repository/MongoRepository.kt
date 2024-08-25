package dev.drewboiii.healthstatsserviceapi.persistence.repository

import dev.drewboiii.healthstatsserviceapi.persistence.model.DayStatisticsEntity
import dev.drewboiii.healthstatsserviceapi.persistence.model.ProviderEntity
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import java.time.LocalDate

@ConditionalOnProperty(name = ["application.mongo.enabled"], havingValue = "true", matchIfMissing = true)
interface DayStatisticsMongoRepository : MongoRepository<DayStatisticsEntity, String> {

    @Query("{ provider: '?0', country: { \$in: ?1 }, reqDate: { \$gte: ?2 } }")
    fun findByProviderAndCountryInAndReqDate(provider: String, countries: List<String>, reqDate: LocalDate): List<DayStatisticsEntity>

    @Query("{ provider: '?0', reqDate: { \$gte: ?1 } }")
    fun findByProviderAndReqDate(provider: String, reqDate: LocalDate): List<DayStatisticsEntity>

}

@ConditionalOnProperty(name = ["application.mongo.enabled"], havingValue = "true", matchIfMissing = true)
interface ProvidersMongoRepository : MongoRepository<ProviderEntity, String>