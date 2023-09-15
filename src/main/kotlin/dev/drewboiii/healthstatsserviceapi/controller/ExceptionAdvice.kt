package dev.drewboiii.healthstatsserviceapi.controller

import dev.drewboiii.healthstatsserviceapi.exception.NotImplementedException
import dev.drewboiii.healthstatsserviceapi.exception.UnknownProviderException
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.ServletWebRequest

private val logger = KotlinLogging.logger {}

@RestControllerAdvice
class ExceptionAdvice {

    @ExceptionHandler(Exception::class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    fun commonExceptionHandler(ex: Exception, request: ServletWebRequest?): String? {
        val url = request?.request?.requestURL
        val parameterNames = request?.parameterMap?.keys
        val errorMessage = "Unknown error occurred \nRequest URL: $url \nParameter names: $parameterNames"
        logger.error(errorMessage, ex)
        return ex.message
    }

    @ExceptionHandler(NotImplementedException::class)
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    fun notImplementedExceptionHandler(ex: NotImplementedException): String? {
        val message = ex.message
        logger.error { message }
        return message
    }

    @ExceptionHandler(value = [UnknownProviderException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun unknownProviderExceptionHandler(ex: UnknownProviderException): String? {
        val message = ex.message
        logger.error { message }
        return message
    }

}