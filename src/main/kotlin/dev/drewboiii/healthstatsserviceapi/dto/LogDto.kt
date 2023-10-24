package dev.drewboiii.healthstatsserviceapi.dto

import dev.drewboiii.healthstatsserviceapi.service.LoggingService
import org.springframework.http.HttpStatus
import java.time.LocalDate
import java.time.LocalTime

data class LogDto(
    val message: String,
    val level: LoggingService.LogLevel,
    val date: LocalDate,
    val time: LocalTime,
    val serviceName: String,
    val exception: Exception? = null,
    val httpStatus: HttpStatus? = null
)
