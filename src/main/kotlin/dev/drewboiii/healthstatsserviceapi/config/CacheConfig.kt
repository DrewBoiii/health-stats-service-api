package dev.drewboiii.healthstatsserviceapi.config

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.boot.autoconfigure.cache.CacheProperties
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.util.Pair

@EnableCaching
@Configuration
@EnableConfigurationProperties(CacheProperties::class)
@ConditionalOnProperty(name = ["application.cache.enabled"], havingValue = "true", matchIfMissing = true)
class CacheConfig(val cachePropertiesMap: CachePropertiesMap) {

    companion object {
        private val EXCLUDED_CACHE_NAMES = setOf("cache-names", "caffeine.spec")
    }

    @Primary
    @Bean("MainCacheManager")
    @ConditionalOnProperty("spring.cache.cache-names")
    fun cacheManager(cacheProperties: CacheProperties): CacheManager {
        val cacheNames = cacheProperties.cacheNames
        val caffeineCacheManager = CaffeineCacheManager(*cacheNames.toTypedArray())

        caffeineCacheManager.setCacheSpecification(cacheProperties.caffeine.spec)

        caffeineCaches().forEach(caffeineCacheManager::registerCustomCache)

        return caffeineCacheManager
    }

    private fun caffeineCaches(): Map<String, Cache<Any, Any>> {
        return cachePropertiesMap.cache.entries.asSequence()
            .filter { cacheNameSpec ->
                !EXCLUDED_CACHE_NAMES.contains(cacheNameSpec.key)
            }
            .map { cacheNameSpec ->
                val cacheName: String = cacheNameSpec.key
                val spec: String = cacheNameSpec.value
                val cache = Caffeine.from(spec).build<Any, Any>()
                Pair.of(cacheName, cache)
            }
            .map { it.first to it.second }
            .toMap()
    }
}