package dev.drewboiii.healthstatsserviceapi.config.properties

import dev.drewboiii.healthstatsserviceapi.exception.ApplicationException
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("application.kafka")
data class AppKafkaPropertiesMap(
    val server: String,
    val topic: Topic
) {

    data class Topic(
        val logs: String
    ) {
        val logsTopic by lazy {
            if (logs.contains("aasd")) throw ApplicationException("asd") else logs
        }
    }

}