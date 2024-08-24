package dev.drewboiii.healthstatsserviceapi.config

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@Configuration
@EnableMongoRepositories(basePackages = ["dev.drewboiii.healthstatsserviceapi.persistence"])
@ConditionalOnProperty(name = ["application.mongo.enabled"], havingValue = "true", matchIfMissing = true)
class MongoConfig