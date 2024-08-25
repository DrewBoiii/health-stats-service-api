package dev.drewboiii.healthstatsserviceapi.controller

import dev.drewboiii.healthstatsserviceapi.dto.NewCountriesToProviderRequest
import dev.drewboiii.healthstatsserviceapi.dto.ProviderResponse
import dev.drewboiii.healthstatsserviceapi.service.CacheService
import dev.drewboiii.healthstatsserviceapi.service.HistoryHealthStatsService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin")
class AdminController(
    private val cacheService: CacheService?,
    private val historyHealthStatsService: HistoryHealthStatsService
) {

    @PostMapping("/caches/invalidate")
    fun invalidateCashes() = cacheService?.evictAllCaches()

    @PostMapping("/countries/add")
    fun addNewCountryToProvider(@RequestBody @Valid request: NewCountriesToProviderRequest): ProviderResponse? =
        historyHealthStatsService.appendCountryToProvider(request)

}