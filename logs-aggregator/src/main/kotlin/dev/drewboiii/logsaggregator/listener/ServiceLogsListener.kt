package dev.drewboiii.logsaggregator.listener

import dev.drewboiii.logsaggregator.dto.LogDto
import mu.KotlinLogging
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger {}

@Component
@ConditionalOnBean(name = ["serviceLogsKafkaListenerContainerFactory"])
class ServiceLogsListener {

    @KafkaListener(
        topics = ["\${kafka-config.consumers.service-logs-topic.topic}"],
        groupId = "\${kafka-config.consumers.service-logs-topic.group-id}",
        containerFactory = "serviceLogsKafkaListenerContainerFactory",
        concurrency = "2"
    )
    fun consume(logDto: LogDto) {
        log.info { "Consumed: $logDto" }
    }

}