package dev.drewboiii.logsaggregator.listener.impl

import dev.drewboiii.logsaggregator.dto.LogDto
import dev.drewboiii.logsaggregator.listener.KafkaMessageHandler
import mu.KotlinLogging
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger {}

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
        log.info { "Treat as error: $message" }
    }

}