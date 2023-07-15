package dev.drewboiii.healthstatsserviceapi.controller

import dev.drewboiii.healthstatsserviceapi.client.Covid19Client
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloWorldController(val client: Covid19Client) {

    @GetMapping("/test")
    fun get() = client.getCountries()

}