package dev.drewboiii.healthstatsserviceapi.aspect

import dev.drewboiii.healthstatsserviceapi.dto.HealthServiceTodayStatsResponse
import dev.drewboiii.healthstatsserviceapi.provider.HealthStatsProviderType
import dev.drewboiii.healthstatsserviceapi.service.*
import mu.KLogging
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component

@Aspect
@Component
class DataSaveAspect(
    private val saveService: DataAspectSaveService,
    private val kafkaService: KafkaService?
) {

    @AfterReturning(
        pointcut = "execution(* dev.drewboiii.healthstatsserviceapi.service.HealthStatsService.getTodayStats(String, String))",
        returning = "todayStats"
    )
    fun saveDayStatistics(joinPoint: JoinPoint, todayStats: HealthServiceTodayStatsResponse) {
        val signature = joinPoint.signature as MethodSignature
        val parameterNames = signature.parameterNames
        val args = joinPoint.args

        val buildArgs = parameterNames.zip(args.map { it as String }).toMap()

        buildArgs["providerName"]?.let {
            saveService.saveDayStats(it, todayStats)
            val message = "Day statistics for $it were saved."
            logger.info { message }
            kafkaService?.sendLog(message, LoggingService.LogLevel.INFO)
        }
    }

    @AfterReturning(
        pointcut = "execution(* dev.drewboiii.healthstatsserviceapi.service.HealthStatsService.getAvailableCountries(String))",
        returning = "countries"
    )
    fun saveAvailableCountries(joinPoint: JoinPoint, countries: List<String>) {
        val signature = joinPoint.signature as MethodSignature
        val parameterNames = signature.parameterNames
        val args = joinPoint.args

        val buildArgs = parameterNames.zip(args.map { it as String }).toMap()

        buildArgs["providerName"]?.let {
            saveService.saveAvailableCountries(HealthStatsProviderType.valueOf(it), countries)
            val message = "Available countries for $it were saved."
            logger.info { message }
            kafkaService?.sendLog(message, LoggingService.LogLevel.INFO)
        }
    }

    companion object: KLogging()

}