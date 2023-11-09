package dev.drewboiii.healthstatsserviceapi.aspect

import dev.drewboiii.healthstatsserviceapi.service.KafkaService
import dev.drewboiii.healthstatsserviceapi.service.LoggingService
import mu.KotlinLogging
import org.apache.catalina.connector.RequestFacade
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

private val logger = KotlinLogging.logger { }

@Aspect
@Component
class LoggingAspect(
    private val kafkaService: KafkaService?
) {

    @Before("@annotation(getMapping)")
    fun logInvocationApiGetMethods(joinPoint: JoinPoint, getMapping: GetMapping) {
        val signature = joinPoint.signature as MethodSignature
        val parameterNames = signature.parameterNames
        val args = joinPoint.args

        val buildArgs = parameterNames.zip(args.map { convertToString(it) }).toMap()

        val path = getMapping.value.takeIf { it.isNotEmpty() }?.iterator()?.next()

        val message = "HTTP GET Request. Path: $path, Method name: ${signature.name}, Args: $buildArgs"

        logger.info { message }

        kafkaService?.sendLogs(message, LoggingService.LogLevel.INFO)
    }

    private fun convertToString(argValue: Any): String = when (argValue) {
        is LocalDate -> argValue.toString()
        is LocalTime -> argValue.toString()
        is LocalDateTime -> argValue.toString()
        is RequestFacade -> argValue.toString()
        else -> argValue as String
    }

}