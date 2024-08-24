package dev.drewboiii.logsaggregator.listener.impl

import dev.drewboiii.logsaggregator.dto.LogDto
import dev.drewboiii.logsaggregator.listener.KafkaMessageHandler
import mu.KLogging
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
@ConditionalOnBean(name = ["serviceLogsKafkaListenerContainerFactory"])
class LogsKafkaMessageHandler : KafkaMessageHandler<LogDto> {

    @KafkaListener(
        topics = ["\${kafka-config.consumers.service-logs-consumer.topic}"],
        groupId = "\${kafka-config.consumers.service-logs-consumer.group-id}",
        containerFactory = "serviceLogsKafkaListenerContainerFactory",
        concurrency = "2"
    )
    override fun handleMessage(message: LogDto) {
        logger.info { "Treat as common log: $message" }
    }

    companion object: KLogging()
}