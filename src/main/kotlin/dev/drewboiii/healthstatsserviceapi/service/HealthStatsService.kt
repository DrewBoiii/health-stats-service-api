package dev.drewboiii.healthstatsserviceapi.service

import dev.drewboiii.healthstatsserviceapi.dto.HealthServiceTodayStatsResponse
import dev.drewboiii.healthstatsserviceapi.exception.CountryNotSupportedException
import dev.drewboiii.healthstatsserviceapi.exception.UnknownProviderException
import dev.drewboiii.healthstatsserviceapi.provider.HealthStatsProvider
import dev.drewboiii.healthstatsserviceapi.provider.HealthStatsProviderType
import kotlinx.coroutines.*
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class HealthStatsService(
    private val providers: List<HealthStatsProvider>
) {

    fun getTodayStats(country: String, providerName: String): HealthServiceTodayStatsResponse {
        val statsProvider = getProvidersMap()[providerName] ?: throw UnknownProviderException(providerName)

        val isCorrectCountry =
            statsProvider.getAvailableCountries().map(String::lowercase).contains(country.lowercase())

        if (!isCorrectCountry) {
            throw CountryNotSupportedException(providerName, country)
        }

        return statsProvider.getTodayStats(country)
    }

    fun getAvailableCountries(providerName: String): List<String> {
        val statsProvider = getProvidersMap()[providerName] ?: throw UnknownProviderException(providerName)

        return statsProvider.getAvailableCountries()
    }

    fun getAvailableProviders() = HealthStatsProviderType.values().map { it.name }.toSet()

    fun getTodayStats(country: String): List<HealthServiceTodayStatsResponse> {
        return runBlocking(Dispatchers.Default) {
            coroutineScope {
                providers.map { it.getProviderName() }
                    .filterNot { it == "CANCER" } //todo exception handling
                    .map {
                        async {
                            getTodayStatsSuspend(country, it)
                                .also { logger.info { "Got data from ${it.providerName}." } }
                        }
                    }
                    .awaitAll()
            }
        }
    }

    private suspend fun getTodayStatsSuspend(country: String, providerName: String): HealthServiceTodayStatsResponse {
        val statsProvider = getProvidersMap()[providerName] ?: throw UnknownProviderException(providerName)

        logger.info { "Getting data from ${statsProvider.getProviderName()}..." }
        return statsProvider.getTodayStats(country)
    }

    private fun getProvidersMap(): Map<String, HealthStatsProvider> = providers.associateBy { it.getProviderName() }

}