package dev.drewboiii.healthstatsserviceapi.client

import feign.RequestInterceptor
import feign.RequestTemplate
import org.springframework.stereotype.Component

@Component
class Covid19ClientHeaderRequestInterceptor: RequestInterceptor {

    override fun apply(template: RequestTemplate?) {
        template?.header("X-RapidAPI-Key", "512a8ec25cmsh69c0e664045c119p1fb528jsn0f5deea831cf")
        template?.header("X-RapidAPI-Host", "covid-193.p.rapidapi.com")
    }

}