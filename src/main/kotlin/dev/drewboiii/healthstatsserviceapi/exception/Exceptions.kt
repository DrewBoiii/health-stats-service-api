package dev.drewboiii.healthstatsserviceapi.exception

open class ApplicationException(message: String) : RuntimeException(message)
class NotImplementedException(message: String = "Not implemented") : ApplicationException(message)
class UnknownProviderException(providerName: String?) :
    ApplicationException(providerName?.let { "Unknown provider - $providerName" } ?: "Unknown provider")

class CountryNotSupportedException(providerName: String, country: String) :
    ApplicationException("Country $country is not supported in provider $providerName")

class NotFoundException(message: String) : ApplicationException(message)

class TooManyRequestsException(message: String = "Too many requests") : ApplicationException(message)
