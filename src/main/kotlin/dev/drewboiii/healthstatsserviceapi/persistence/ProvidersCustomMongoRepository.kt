package dev.drewboiii.healthstatsserviceapi.persistence

interface ProvidersCustomMongoRepository {

    fun appendNewCountryToProvider(provider: String, countries: Set<String>): ProviderEntity?

}