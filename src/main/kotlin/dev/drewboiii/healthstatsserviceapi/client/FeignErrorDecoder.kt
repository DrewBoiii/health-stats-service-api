package dev.drewboiii.healthstatsserviceapi.client

import feign.Response
import feign.codec.ErrorDecoder
import org.springframework.stereotype.Component
import java.lang.Exception

@Component
class FeignErrorDecoder: ErrorDecoder {

    override fun decode(methodKey: String, response: Response): Exception =
        when(response.status()) {
            400 -> throw RuntimeException("Bad Request")
            404 -> throw RuntimeException("Not Found")
            else -> throw RuntimeException("method - $methodKey, status - ${response.status()}, reason - ${response.reason()}")
        }

}