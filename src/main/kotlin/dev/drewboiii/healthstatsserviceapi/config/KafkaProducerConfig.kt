package dev.drewboiii.healthstatsserviceapi.config

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer

@Configuration
class KafkaProducerConfig(
    @Value("\${application.kafka.server:localhost:29092}")
    private val kafkaServer: String
) {

    @Bean
    fun kafkaJsonTemplate(): KafkaTemplate<String, Any> = KafkaTemplate(producerJsonFactory())

    @Bean
    fun producerConfigs() = mapOf(
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaServer,
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java
    )

    @Bean
    fun producerJsonFactory(): ProducerFactory<String, Any> = DefaultKafkaProducerFactory(producerConfigs())

}