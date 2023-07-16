package dev.drewboiii.healthstatsserviceapi.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("spring")
data class CachePropertiesMap(var cache: Map<String, String>)