package dev.drewboiii.healthstatsserviceapi.config

import dev.drewboiii.healthstatsserviceapi.config.properties.AppKafkaPropertiesMap
import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.common.config.TopicConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.KafkaAdmin

@Configuration
class KafkaTopicConfig(
    private val appKafkaPropertiesMap: AppKafkaPropertiesMap
) {

    @Bean
    fun admin(): KafkaAdmin {
        val configs = mapOf(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG to appKafkaPropertiesMap.server)
        return KafkaAdmin(configs)
    }

    @Bean
    fun serviceLogsTopic(): NewTopic = TopicBuilder
        .name(appKafkaPropertiesMap.topic.logs)
        .config(TopicConfig.COMPRESSION_TYPE_CONFIG, "zstd")
        .build()

}