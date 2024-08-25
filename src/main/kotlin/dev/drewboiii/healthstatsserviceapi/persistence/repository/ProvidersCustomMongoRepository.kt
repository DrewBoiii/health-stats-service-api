package dev.drewboiii.healthstatsserviceapi.persistence.repository

import dev.drewboiii.healthstatsserviceapi.persistence.model.ProviderEntity

interface ProvidersCustomMongoRepository {

    fun appendNewCountryToProvider(provider: String, countries: Set<String>): ProviderEntity?

}