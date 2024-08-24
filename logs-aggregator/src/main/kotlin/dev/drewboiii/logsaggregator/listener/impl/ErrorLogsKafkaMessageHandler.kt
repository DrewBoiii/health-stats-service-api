package dev.drewboiii.logsaggregator.listener.impl

import dev.drewboiii.logsaggregator.dto.LogDto
import dev.drewboiii.logsaggregator.listener.KafkaMessageHandler
import mu.KLogging
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
@ConditionalOnBean(name = ["serviceErrorLogsKafkaListenerContainerFactory"])
class ErrorLogsKafkaMessageHandler : KafkaMessageHandler<LogDto> {

    @KafkaListener(
        topics = ["\${kafka-config.consumers.service-error-logs-consumer.topic}"],
        groupId = "\${kafka-config.consumers.service-error-logs-consumer.group-id}",
        containerFactory = "serviceErrorLogsKafkaListenerContainerFactory",
        concurrency = "2"
    )
    override fun handleMessage(message: LogDto) {
        logger.info { "Treat as error: $message" }
    }

    companion object : KLogging()

}