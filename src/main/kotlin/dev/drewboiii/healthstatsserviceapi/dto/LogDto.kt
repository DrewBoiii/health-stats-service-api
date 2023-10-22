package dev.drewboiii.healthstatsserviceapi.dto

import dev.drewboiii.healthstatsserviceapi.aspect.LoggingAspect
import org.springframework.http.HttpStatus
import java.time.LocalDate
import java.time.LocalTime

data class LogDto(
    val message: String,
    val level: LoggingAspect.LogLevel,
    val date: LocalDate,
    val time: LocalTime,
    val serviceName: String,
    val exception: Exception? = null,
    val httpStatus: HttpStatus? = null
)
