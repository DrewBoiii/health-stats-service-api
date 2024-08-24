package dev.drewboiii.healthstatsserviceapi.persistence.impl

import dev.drewboiii.healthstatsserviceapi.persistence.CountryEntity
import dev.drewboiii.healthstatsserviceapi.persistence.ProviderEntity
import dev.drewboiii.healthstatsserviceapi.persistence.ProvidersCustomMongoRepository
import org.springframework.data.mongodb.core.FindAndModifyOptions
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ProvidersCustomMongoRepositoryImpl(
    private val mongoTemplate: MongoTemplate
) : ProvidersCustomMongoRepository {

    override fun appendNewCountryToProvider(provider: String, countries: Set<String>): ProviderEntity? =
        mongoTemplate.findAndModify(
            Query.query(
                where(ProviderEntity::name.name).`is`(provider)
                    .andOperator(where("countries.name").nin(countries))
            ),
            Update().addToSet(ProviderEntity::countries.name)
                .each(countries.map {
                    CountryEntity(
                        id = UUID.randomUUID().toString(),
                        name = it
                    )
                }),
            FindAndModifyOptions().returnNew(true),
            ProviderEntity::class.java
        )
}