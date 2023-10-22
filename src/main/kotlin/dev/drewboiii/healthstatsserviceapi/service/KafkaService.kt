package dev.drewboiii.healthstatsserviceapi.service

import dev.drewboiii.healthstatsserviceapi.aspect.LoggingAspect
import dev.drewboiii.healthstatsserviceapi.config.properties.AppKafkaPropertiesMap
import dev.drewboiii.healthstatsserviceapi.dto.LogDto
import dev.drewboiii.healthstatsserviceapi.exception.ApplicationException
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalTime

@Service
class KafkaService(
    @Value("\${application.name:health-stats-service-api")
    private val serverName: String,
    private val appKafkaPropertiesMap: AppKafkaPropertiesMap,
    private val kafkaProducerService: KafkaProducerService
) {

    fun sendLogs(
        message: String,
        logLevel: LoggingAspect.LogLevel,
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

        val headers = mapOf(
            KafkaHeaders.TOPIC to appKafkaPropertiesMap.topic.logs
        )

        kafkaProducerService.sendMessage(payload, headers)
    }

}