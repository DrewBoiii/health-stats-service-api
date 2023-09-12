package dev.drewboiii.healthstatsserviceapi.aspect

import dev.drewboiii.healthstatsserviceapi.dto.HealthServiceTodayStatsResponse
import dev.drewboiii.healthstatsserviceapi.service.CassandraService
import mu.KotlinLogging
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger { }

@Aspect
@Component
class CassandraSaverAspect(
    private val cassandraService: CassandraService
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

        buildArgs["providerName"]?.let { cassandraService.saveDayStats(it, todayStats) }
    }

}