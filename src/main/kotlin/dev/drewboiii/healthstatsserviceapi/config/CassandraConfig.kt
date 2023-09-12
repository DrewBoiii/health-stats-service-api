package dev.drewboiii.healthstatsserviceapi.config

import dev.drewboiii.healthstatsserviceapi.converter.HealthStatsProviderTypeToStringConverter
import jakarta.annotation.PostConstruct
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration
import org.springframework.data.cassandra.core.convert.CassandraCustomConversions
import org.springframework.data.cassandra.core.cql.session.init.KeyspacePopulator
import org.springframework.data.cassandra.core.cql.session.init.ResourceKeyspacePopulator
import org.springframework.util.StreamUtils
import java.nio.charset.StandardCharsets


@Configuration
@EnableConfigurationProperties(CassandraProperties::class)
class CassandraConfig(
    private val cassandraProperties: CassandraProperties
) : AbstractCassandraConfiguration() {

    override fun getKeyspaceName(): String = cassandraProperties.keyspaceName

    override fun keyspacePopulator(): KeyspacePopulator =
        ResourceKeyspacePopulator(ClassPathResource("schema/cassandra_keyspace.cql"))

    @Bean
    override fun customConversions(): CassandraCustomConversions =
        CassandraCustomConversions(listOf(HealthStatsProviderTypeToStringConverter()))

    @PostConstruct
    fun initializeSchema() {
        val schema = ClassPathResource("schema/cassandra_schema.cql")
        val cql = StreamUtils.copyToString(schema.inputStream, StandardCharsets.UTF_8)
        cql.split(';').asSequence()
            .map(String::trim)
            .filterNot(String::isEmpty)
            .forEach { super.cassandraTemplate().cqlOperations.execute(it) }
    }

}