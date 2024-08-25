package dev.drewboiii.healthstatsserviceapi.config

import dev.drewboiii.healthstatsserviceapi.converter.HealthStatsProviderTypeToStringConverter
import jakarta.annotation.PostConstruct
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration
import org.springframework.data.cassandra.config.CqlSessionFactoryBean
import org.springframework.data.cassandra.core.convert.CassandraCustomConversions
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories
import org.springframework.util.StreamUtils
import java.nio.charset.StandardCharsets


@Configuration
@EnableCassandraRepositories
@EnableConfigurationProperties(CassandraProperties::class)
@ConditionalOnProperty(name = ["application.cassandra.enabled"], havingValue = "true", matchIfMissing = true)
class CassandraConfig(
    private val cassandraProperties: CassandraProperties
) : AbstractCassandraConfiguration() {

    override fun getKeyspaceName(): String = cassandraProperties.keyspaceName

    @Bean
    override fun customConversions(): CassandraCustomConversions =
        CassandraCustomConversions(listOf(HealthStatsProviderTypeToStringConverter()))

    @Bean
    override fun cassandraSession(): CqlSessionFactoryBean {
        val cassandraSession = super.cassandraSession()
        cassandraSession.setKeyspaceStartupScripts(listOf(getSchemaKeyspaceScript()))
        cassandraSession.setUsername(cassandraProperties.username)
        cassandraSession.setPassword(cassandraProperties.password)
        return cassandraSession
    }

    @PostConstruct
    fun initializeSchema() {
        val schema = ClassPathResource("schema/cassandra_schema.cql")
        val cql = StreamUtils.copyToString(schema.inputStream, StandardCharsets.UTF_8)
        cql.split(';').asSequence()
            .map(String::trim)
            .filterNot(String::isEmpty)
            .forEach { super.cassandraTemplate().cqlOperations.execute(it) }
    }

    private fun getSchemaKeyspaceScript() =
        StreamUtils.copyToString(ClassPathResource("schema/cassandra_keyspace.cql").inputStream, StandardCharsets.UTF_8)

}