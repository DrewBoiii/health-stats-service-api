package dev.drewboiii.healthstatsserviceapi.aspect

import dev.drewboiii.healthstatsserviceapi.dto.HealthServiceTodayStatsResponse
import dev.drewboiii.healthstatsserviceapi.provider.HealthStatsProviderType
import dev.drewboiii.healthstatsserviceapi.service.CassandraService
import dev.drewboiii.healthstatsserviceapi.service.KafkaService
import dev.drewboiii.healthstatsserviceapi.service.LoggingService.LogLevel
import mu.KotlinLogging
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger { }

@Aspect
@Component
@ConditionalOnProperty(name = ["application.cassandra.enabled"], havingValue = "true", matchIfMissing = true)
class CassandraSaveAspect(
    private val cassandraService: CassandraService,
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
            cassandraService.saveDayStats(it, todayStats)
            val message = "Day statistics for $it were saved."
            logger.info { message }
            kafkaService?.sendLog(message, LogLevel.INFO)
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
            cassandraService.saveAvailableCountries(HealthStatsProviderType.valueOf(it), countries)
            val message = "Available countries for $it were saved."
            logger.info { message }
            kafkaService?.sendLog(message, LogLevel.INFO)
        }
    }

}