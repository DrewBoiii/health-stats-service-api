package dev.drewboiii.logsaggregator.config

import dev.drewboiii.logsaggregator.dto.LogDto
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer
import org.springframework.kafka.support.serializer.JsonDeserializer

@Configuration
class KafkaConsumerConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "kafka-config")
    fun kafkaConfigProperties(): KafkaCustomProperties = KafkaCustomProperties()

    @Bean
    @ConditionalOnProperty(
        value = ["kafka-config.consumers.service-logs-topic.enabled"],
        havingValue = "true",
        matchIfMissing = true
    )
    fun serviceLogsKafkaListenerContainerFactory(): KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, LogDto>> =
        jsonKafkaListener(kafkaConfigProperties(), "service-logs-topic", LogDto::class.java)

    private fun <T> jsonKafkaListener(
        properties: KafkaCustomProperties,
        consumerName: String,
        modelClass: Class<T>
    ): ConcurrentKafkaListenerContainerFactory<String, T> {
        val valueDeserializer = ErrorHandlingDeserializer<T>(JsonDeserializer(modelClass, false))

        val defaultKafkaConsumerFactory = ConcurrentKafkaListenerContainerFactory<String, T>()
        defaultKafkaConsumerFactory.consumerFactory =
            consumerFactory(consumerName, properties, StringDeserializer(), valueDeserializer)

        return defaultKafkaConsumerFactory
    }

    private fun <T> consumerFactory(
        consumerName: String,
        properties: KafkaCustomProperties,
        keyDeserializer: StringDeserializer,
        valueDeserializer: ErrorHandlingDeserializer<T>
    ): ConsumerFactory<String, T> =
        DefaultKafkaConsumerFactory(
            consumerSettings(consumerName, properties),
            keyDeserializer,
            valueDeserializer
        )

    private fun consumerSettings(consumerName: String, kafkaCustomProperties: KafkaCustomProperties): Map<String, Any> {
        val consumer = kafkaCustomProperties.consumers[consumerName]
        val consumerProperties = (consumer?.buildProperties() ?: mapOf()).toMutableMap()

        val properties = kafkaCustomProperties.buildProperties()

        properties.putAll(consumerProperties)

        return properties
    }

}