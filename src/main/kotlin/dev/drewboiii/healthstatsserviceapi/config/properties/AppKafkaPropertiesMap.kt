package dev.drewboiii.healthstatsserviceapi.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("application.kafka")
data class AppKafkaPropertiesMap(
    val server: String,
    val topic: Topic
) {

    data class Topic(
        val logs: String
    )

}