package dev.drewboiii.healthstatsserviceapi.service

import mu.KLogging
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.CacheEvict
import org.springframework.stereotype.Service

@Service
@ConditionalOnProperty(name = ["application.cache.enabled"], havingValue = "true", matchIfMissing = true)
class CacheService(
    private val cacheManager: CacheManager
) {

    @CacheEvict(value = ["covid-today-stats-cache"], allEntries = true, cacheManager = "MainCacheManager")
    fun evictCovidTodayStatsCache() {
    }

    fun evictAllCaches() = cacheManager.cacheNames.forEach { cacheManager.getCache(it)?.clear() }
        .also { logger.info { "Cache evicted." } }

    companion object: KLogging()
}