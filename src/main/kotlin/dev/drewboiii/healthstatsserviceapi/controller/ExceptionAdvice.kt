package dev.drewboiii.healthstatsserviceapi.controller

import dev.drewboiii.healthstatsserviceapi.aspect.LoggingAspect
import dev.drewboiii.healthstatsserviceapi.exception.NotFoundException
import dev.drewboiii.healthstatsserviceapi.exception.NotImplementedException
import dev.drewboiii.healthstatsserviceapi.exception.UnknownProviderException
import dev.drewboiii.healthstatsserviceapi.service.KafkaService
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.ServletWebRequest

private val logger = KotlinLogging.logger {}

@RestControllerAdvice
class ExceptionAdvice(
    private val kafkaService: KafkaService
) {

    @ExceptionHandler(Exception::class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    fun commonExceptionHandler(ex: Exception, request: ServletWebRequest?): String? {
        val url = request?.request?.requestURL
        val parameterNames = request?.parameterMap?.keys
        val errorMessage = "Unknown error occurred \nRequest URL: $url \nParameter names: $parameterNames"
        logger.error(errorMessage, ex)
        kafkaService.sendLogs(errorMessage, LoggingAspect.LogLevel.ERROR, ex, HttpStatus.INTERNAL_SERVER_ERROR)
        return ex.message
    }

    @ExceptionHandler(NotImplementedException::class)
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    fun notImplementedExceptionHandler(ex: NotImplementedException): String? {
        val message = ex.message ?: "Not Implemented"
        logger.error { message }
        kafkaService.sendLogs(message, LoggingAspect.LogLevel.ERROR, ex, HttpStatus.NOT_IMPLEMENTED)
        return message
    }

    @ExceptionHandler(value = [UnknownProviderException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun unknownProviderExceptionHandler(ex: UnknownProviderException): String? {
        val message = ex.message ?: "Bad Request"
        logger.error { message }
        kafkaService.sendLogs(message, LoggingAspect.LogLevel.ERROR, ex, HttpStatus.BAD_REQUEST)
        return message
    }

    @ExceptionHandler(value = [NotFoundException::class])
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun notFoundExceptionHandler(ex: NotFoundException): String? {
        val message = ex.message ?: "Not Found"
        logger.error { message }
        kafkaService.sendLogs(message, LoggingAspect.LogLevel.ERROR, ex, HttpStatus.NOT_FOUND)
        return message
    }

}