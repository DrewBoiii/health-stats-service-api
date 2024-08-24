package dev.drewboiii.healthstatsserviceapi.aspect

import dev.drewboiii.healthstatsserviceapi.service.KafkaService
import dev.drewboiii.healthstatsserviceapi.service.LoggingService
import mu.KLogging
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

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

        val buildArgs = parameterNames.zip(args.map { it }).toMap()

        val path = getMapping.value.takeIf { it.isNotEmpty() }?.iterator()?.next()

        val message = "HTTP GET Request. Path: $path, Method name: ${signature.name}, Args: $buildArgs"

        logger.info { message }

        kafkaService?.sendLog(message, LoggingService.LogLevel.INFO)
    }

    @Before("@annotation(postMapping)")
    fun logInvocationApiPostMethods(joinPoint: JoinPoint, postMapping: PostMapping) {
        val signature = joinPoint.signature as MethodSignature
        val parameterNames = signature.parameterNames
        val args = joinPoint.args

        val buildArgs = parameterNames.zip(args.map { it }).toMap()

        val path = postMapping.value.takeIf { it.isNotEmpty() }?.iterator()?.next()

        val message = "HTTP POST Request. Path: $path, Method name: ${signature.name}, Args: $buildArgs"

        logger.info { message }

        kafkaService?.sendLog(message, LoggingService.LogLevel.INFO)
    }

    companion object : KLogging()
}