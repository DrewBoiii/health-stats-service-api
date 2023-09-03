package dev.drewboiii.healthstatsserviceapi.converter

import dev.drewboiii.healthstatsserviceapi.provider.HealthStatsProviderType
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.WritingConverter

@WritingConverter
class HealthStatsProviderTypeToStringConverter: Converter<HealthStatsProviderType, String> {

    override fun convert(source: HealthStatsProviderType): String = source.name

}