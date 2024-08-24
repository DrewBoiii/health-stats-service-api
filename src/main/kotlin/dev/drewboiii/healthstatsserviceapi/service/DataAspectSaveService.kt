package dev.drewboiii.healthstatsserviceapi.service

import dev.drewboiii.healthstatsserviceapi.dto.HealthServiceTodayStatsResponse
import dev.drewboiii.healthstatsserviceapi.provider.HealthStatsProviderType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service

@Service
class DataAspectSaveService(
    private val persistableServices: List<AspectHealthStatsPersistable>
) {

    fun saveDayStats(providerName: String, todayStats: HealthServiceTodayStatsResponse) =
        runBlocking(Dispatchers.Default) {
            coroutineScope {
                persistableServices.forEach {
                    launch { it.saveDayStats(providerName, todayStats) }
                }
            }
        }

    fun saveAvailableCountries(provider: HealthStatsProviderType, countries: List<String>) =
        runBlocking(Dispatchers.Default) {
            coroutineScope {
                persistableServices.forEach {
                    launch { it.saveAvailableCountries(provider, countries) }
                }
            }
        }

}