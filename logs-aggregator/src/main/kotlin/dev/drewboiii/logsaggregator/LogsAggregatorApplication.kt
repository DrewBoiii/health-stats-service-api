package dev.drewboiii.logsaggregator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LogsAggregatorApplication

fun main(args: Array<String>) {
	runApplication<LogsAggregatorApplication>(*args)
}
