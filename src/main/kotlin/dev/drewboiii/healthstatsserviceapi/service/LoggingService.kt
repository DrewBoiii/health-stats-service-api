package dev.drewboiii.healthstatsserviceapi.service

import org.springframework.stereotype.Service

@Service
class LoggingService {

    enum class LogLevel {
        INFO, WARN, ERROR, DEBUG
    }

}