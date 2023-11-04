package dev.drewboiii.healthstatsserviceapi.client

import dev.drewboiii.healthstatsserviceapi.exception.ApplicationException
import feign.Response
import feign.codec.ErrorDecoder
import org.springframework.stereotype.Component
import java.lang.Exception

@Component
class FeignErrorDecoder: ErrorDecoder {

    override fun decode(methodKey: String, response: Response): Exception =
        when(response.status()) {
            400 -> throw ApplicationException("Client responded with bad request")
            404 -> throw ApplicationException("Client responded with not found")
            500 -> throw ApplicationException("Client is unavailable")
            else -> throw RuntimeException("Client responded with error, Method - $methodKey, status - ${response.status()}, reason - ${response.reason()}")
        }

}