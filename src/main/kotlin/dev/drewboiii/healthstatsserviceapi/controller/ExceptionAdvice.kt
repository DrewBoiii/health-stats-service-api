package dev.drewboiii.healthstatsserviceapi.controller

import dev.drewboiii.healthstatsserviceapi.exception.*
import dev.drewboiii.healthstatsserviceapi.service.KafkaService
import dev.drewboiii.healthstatsserviceapi.service.LoggingService
import feign.RetryableException
import io.github.resilience4j.circuitbreaker.CallNotPermittedException
import jakarta.annotation.PostConstruct
import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.ServletWebRequest

@RestControllerAdvice
class ExceptionAdvice(
    private val kafkaService: KafkaService?
) {

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun commonExceptionHandler(ex: Exception, request: ServletWebRequest?): String? {
        val url = request?.request?.requestURL
        val parameterNames = request?.parameterMap?.keys
        val errorMessage = "Unknown error occurred \nRequest URL: $url \nParameter names: $parameterNames"
        logger.error(errorMessage, ex)
        kafkaService?.sendLog(errorMessage, LoggingService.LogLevel.ERROR, ex, HttpStatus.INTERNAL_SERVER_ERROR)
        return errorMessage
    }

    @ExceptionHandler(*[CallNotPermittedException::class, RetryableException::class, ApplicationException::class])
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    fun serviceNotAvailableExceptionHandler(ex: RuntimeException, request: ServletWebRequest?): String? {
        val url = request?.request?.requestURL
        val parameterNames = request?.parameterMap?.keys
        val errorMessage = "Service is unavailable, provider is not connected \nRequest URL: $url \nParameter names: $parameterNames"
        logger.error(errorMessage, ex)
        kafkaService?.sendLog(errorMessage, LoggingService.LogLevel.ERROR, ex, HttpStatus.SERVICE_UNAVAILABLE)
        return errorMessage
    }

    @ExceptionHandler(NotImplementedException::class)
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    fun notImplementedExceptionHandler(ex: NotImplementedException): String? {
        val message = ex.message ?: "Not Implemented"
        logger.error(ex) { message }
        kafkaService?.sendLog(message, LoggingService.LogLevel.ERROR, ex, HttpStatus.NOT_IMPLEMENTED)
        return message
    }

    @ExceptionHandler(value = [UnknownProviderException::class, CountryNotSupportedException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun badRequestExceptionHandler(ex: ApplicationException): String? {
        val message = ex.message ?: "Bad Request"
        logger.error(ex) { message }
        kafkaService?.sendLog(message, LoggingService.LogLevel.ERROR, ex, HttpStatus.BAD_REQUEST)
        return message
    }

    @ExceptionHandler(value = [NotFoundException::class])
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun notFoundExceptionHandler(ex: NotFoundException): String? {
        val message = ex.message ?: "Not Found"
        logger.error(ex) { message }
        kafkaService?.sendLog(message, LoggingService.LogLevel.ERROR, ex, HttpStatus.NOT_FOUND)
        return message
    }

    @ExceptionHandler(value = [HttpRequestMethodNotSupportedException::class])
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    fun methodNotAllowedExceptionHandler(ex: HttpRequestMethodNotSupportedException): String? {
        val message = ex.message ?: "Method Not Allowed"
        logger.error(ex) { message }
        kafkaService?.sendLog(message, LoggingService.LogLevel.ERROR, ex, HttpStatus.METHOD_NOT_ALLOWED)
        return message
    }

    @ExceptionHandler(value = [TooManyRequestsException::class])
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    fun tooManyRequestsExceptionHandler(ex: TooManyRequestsException): String? {
        val message = ex.message ?: "Too Many Requests"
        logger.error(ex) { message }
        kafkaService?.sendLog(message, LoggingService.LogLevel.ERROR, ex, HttpStatus.TOO_MANY_REQUESTS)
        return message
    }

    @PostConstruct
    fun init() = Thread.setDefaultUncaughtExceptionHandler { thread, exception ->
        logger.error(exception) { "Uncaught exception in thread ${thread.name}: ${exception.message}" }
    }

    companion object: KLogging()

}