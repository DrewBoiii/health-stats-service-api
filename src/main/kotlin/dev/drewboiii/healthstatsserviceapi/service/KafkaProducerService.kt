package dev.drewboiii.healthstatsserviceapi.service

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture

@Service
@ConditionalOnProperty(name = ["application.kafka.enabled"], havingValue = "true", matchIfMissing = true)
class KafkaProducerService(
    private val kafkaTemplate: KafkaTemplate<String, Any>
) {

    fun sendMessage(payload: Any, headers: Map<String, String>): CompletableFuture<SendResult<String, Any>> {
        val messageBuilder = MessageBuilder.withPayload(payload)
        headers.forEach { messageBuilder.setHeader(it.key, it.value) }
        val message = messageBuilder.build()

        return kafkaTemplate.send(message)
    }

    fun sendMessages(payload: List<Any>, headers: Map<String, String>): Boolean {
        val messages = payload.map {
            val messageBuilder = MessageBuilder.withPayload(payload)
            headers.forEach { messageBuilder.setHeader(it.key, it.value) }
            messageBuilder.build()
        }

        return kafkaTemplate.executeInTransaction { template ->
            messages.forEach { template.send(it) }
            return@executeInTransaction true
        }
    }

}