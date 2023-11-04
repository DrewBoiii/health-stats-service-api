package dev.drewboiii.healthstatsserviceapi.controller

import dev.drewboiii.healthstatsserviceapi.service.CacheService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin")
class AdminController(
    private val cacheService: CacheService?
) {

    @PostMapping("/caches/invalidate")
    fun invalidateCashes() = cacheService?.evictAllCaches()

}