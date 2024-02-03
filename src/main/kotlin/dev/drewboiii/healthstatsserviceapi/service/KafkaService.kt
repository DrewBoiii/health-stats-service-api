package dev.drewboiii.healthstatsserviceapi.service

import dev.drewboiii.healthstatsserviceapi.config.properties.AppKafkaPropertiesMap
import dev.drewboiii.healthstatsserviceapi.dto.LogDto
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.http.HttpStatus
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalTime

private val logger = KotlinLogging.logger { }

@Service
@ConditionalOnProperty(name = ["application.kafka.enabled"], havingValue = "true", matchIfMissing = true)
class KafkaService(
    @Value("\${application.name:health-stats-service-api}")
    private val serverName: String,
    private val appKafkaPropertiesMap: AppKafkaPropertiesMap,
    private val kafkaProducerService: KafkaProducerService
) {

    fun sendLog(
        message: String,
        logLevel: LoggingService.LogLevel,
        exception: Exception? = null,
        httpStatus: HttpStatus? = null
    ) {
        val payload = LogDto(
            message = message,
            level = logLevel,
            date = LocalDate.now(),
            time = LocalTime.now(),
            serviceName = serverName,
            exception = exception,
            httpStatus = httpStatus
        )

        val headers = mapOf(KafkaHeaders.TOPIC to appKafkaPropertiesMap.topic.logs)

        kafkaProducerService.sendMessage(payload, headers)
            .whenComplete { result, ex ->
                ex?.let { logger.error { it.message } }
                result?.let { logger.info { "Message was successfully delivered to the topic - ${it.producerRecord.topic()}" } }
            }
    }

    fun sendLogs(
        messages: List<String>,
        logLevel: LoggingService.LogLevel,
        exception: Exception? = null,
        httpStatus: HttpStatus? = null
    ) {
        val headers = mapOf(KafkaHeaders.TOPIC to appKafkaPropertiesMap.topic.logs)

        messages.map {
            LogDto(
                message = it,
                level = logLevel,
                date = LocalDate.now(),
                time = LocalTime.now(),
                serviceName = serverName,
                exception = exception,
                httpStatus = httpStatus
            )
        }.apply {
            kafkaProducerService.sendMessages(this, headers)
        }
    }

}