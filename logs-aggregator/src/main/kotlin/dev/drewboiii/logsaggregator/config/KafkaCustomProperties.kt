package dev.drewboiii.logsaggregator.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.springframework.boot.autoconfigure.kafka.KafkaProperties

class KafkaCustomProperties constructor() : KafkaProperties() {
    lateinit var consumers: Map<String, Consumer>

    fun buildProperties(): MutableMap<String, Any> = mutableMapOf(
        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers
    )
}