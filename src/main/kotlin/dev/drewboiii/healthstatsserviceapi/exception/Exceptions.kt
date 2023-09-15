package dev.drewboiii.healthstatsserviceapi.exception

class NotImplementedException(message: String = "Not implemented") : RuntimeException(message)
class UnknownProviderException(providerName: String?) : RuntimeException(providerName?.let { "Unknown provider - $providerName" } ?: "Unknown provider")
