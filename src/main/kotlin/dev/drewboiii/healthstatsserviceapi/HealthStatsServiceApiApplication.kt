package dev.drewboiii.healthstatsserviceapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HealthStatsServiceApiApplication

fun main(args: Array<String>) {
	runApplication<HealthStatsServiceApiApplication>(*args)
}
