package dev.drewboiii.healthstatsserviceapi.client

import dev.drewboiii.healthstatsserviceapi.exception.ApplicationException
import feign.Response
import feign.codec.ErrorDecoder
import org.springframework.stereotype.Component

@Component
class FeignErrorDecoder : ErrorDecoder {

    override fun decode(methodKey: String, response: Response): Exception =
        when (response.status()) {
            429 -> throw ApplicationException("Client responded with too many Requests")
            500 -> throw ApplicationException("Client is unavailable")
            else -> throw RuntimeException("Client responded with error, Method - $methodKey, status - ${response.status()}, reason - ${response.reason()}")
        }

}