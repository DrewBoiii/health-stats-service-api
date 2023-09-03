package dev.drewboiii.healthstatsserviceapi.service

import dev.drewboiii.healthstatsserviceapi.dto.HealthServiceTodayStatsResponse
import dev.drewboiii.healthstatsserviceapi.dto.toCassandraModel
import dev.drewboiii.healthstatsserviceapi.persistence.DayStatisticsCassandraRepository
import org.springframework.stereotype.Service

@Service
class CassandraService(
    private val dayStatsRepository: DayStatisticsCassandraRepository
) {

    fun saveDayStats(providerName: String, todayStats: HealthServiceTodayStatsResponse) =
        dayStatsRepository.save(todayStats.toCassandraModel(providerName))

}