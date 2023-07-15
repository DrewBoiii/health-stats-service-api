package dev.drewboiii.healthstatsserviceapi.client

import dev.drewboiii.healthstatsserviceapi.config.Covid19FeignConfig
import dev.drewboiii.healthstatsserviceapi.dto.Covid19CountriesResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(name = "covid", url = "\${feign.client.config.covid.api.url}", configuration = [Covid19FeignConfig::class])
interface Covid19Client {

    @GetMapping("/countries")
    fun getCountries(): Covid19CountriesResponse

}