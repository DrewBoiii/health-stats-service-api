package dev.drewboiii.healthstatsserviceapi.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloWorldController {

    @GetMapping("/greetings")
    fun hello() = "Hello World"

}