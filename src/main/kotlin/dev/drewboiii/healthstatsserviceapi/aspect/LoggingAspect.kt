package dev.drewboiii.healthstatsserviceapi.aspect

import dev.drewboiii.healthstatsserviceapi.service.KafkaService
import mu.KotlinLogging
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
    private val kafkaService: KafkaService
) {

    enum class LogLevel {
        INFO, WARN, ERROR, DEBUG
    }

    @Before("@annotation(getMapping)")
    fun logInvocationApiGetMethods(joinPoint: JoinPoint, getMapping: GetMapping) {
        val signature = joinPoint.signature as MethodSignature
        val parameterNames = signature.parameterNames
        val args = joinPoint.args

        val buildArgs = parameterNames.zip(args.map { convertToString(it) }).toMap()

        val path = getMapping.value.takeIf { it.isNotEmpty() }?.iterator()?.next()

        val message = "HTTP GET Request. Path: $path, Method name: ${signature.name}, Args: $buildArgs"

        logger.info { message }

        kafkaService.sendLogs(message, LogLevel.INFO)
    }

    private fun convertToString(argValue: Any) = when (argValue) {
        is LocalDate -> argValue.toString()
        is LocalTime -> argValue.toString()
        is LocalDateTime -> argValue.toString()
        else -> argValue as String
    }

}