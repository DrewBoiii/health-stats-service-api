package dev.drewboiii.healthstatsserviceapi.persistence

import org.springframework.data.cassandra.repository.CassandraRepository
import java.util.UUID

interface DayStatisticsCassandraRepository: CassandraRepository<DayStatistics, UUID>