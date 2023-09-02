package dev.drewboiii.healthstatsserviceapi.client

import feign.RequestInterceptor
import feign.RequestTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class Covid19ClientHeaderRequestInterceptor: RequestInterceptor {

    @Value("\${application.client.interceptor.covid19.api-key}") private var apiKey: String? = null
    @Value("\${application.client.interceptor.covid19.api-host}") private var apiHost: String? = null

    override fun apply(template: RequestTemplate?) {
        template?.header("X-RapidAPI-Key", apiKey)
        template?.header("X-RapidAPI-Host", apiHost)
    }

}