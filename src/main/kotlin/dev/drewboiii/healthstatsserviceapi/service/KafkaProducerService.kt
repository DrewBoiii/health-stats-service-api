package dev.drewboiii.healthstatsserviceapi.service

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture

@Service
class KafkaProducerService(
    private val kafkaTemplate: KafkaTemplate<String, Any>
) {

    fun sendMessage(payload: Any, headers: Map<String, String>): CompletableFuture<SendResult<String, Any>> {
        val messageBuilder = MessageBuilder.withPayload(payload)
        headers.forEach { messageBuilder.setHeader(it.key, it.value) }
        val message = messageBuilder.build()

        return kafkaTemplate.send(message)
    }

}