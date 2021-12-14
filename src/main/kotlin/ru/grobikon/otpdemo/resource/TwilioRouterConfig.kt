package ru.grobikon.otpdemo.resource

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.*

@Configuration
class TwilioRouterConfig(
    val handler: TwilioOTPHandler
) {

    @Bean
    fun handleSMS(): RouterFunction<ServerResponse> {
        return RouterFunctions.route()
            .POST("/router/sendOTP") { serverRequest -> handler.sendOTP(serverRequest) }
            .POST("/router/validateOTP") { serverRequest -> handler.validateOTP(serverRequest) }
            .build()
    }
}