package ru.grobikon.otpdemo.resource

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import ru.grobikon.otpdemo.dto.PasswordResetRequestDto
import ru.grobikon.otpdemo.service.TwilioOTPService


@Component
class TwilioOTPHandler(
    val service: TwilioOTPService
) {

    fun sendOTP(serverRequest: ServerRequest): Mono<ServerResponse> {
        return serverRequest.bodyToMono(PasswordResetRequestDto::class.java)
            .flatMap { dto -> service.sendOTPForPasswordReset(dto) }
            .flatMap { dto -> ServerResponse.status(HttpStatus.OK).body(BodyInserters.fromValue(dto)) }
    }

    fun validateOTP(serverRequest: ServerRequest): Mono<ServerResponse> {
        return serverRequest.bodyToMono(PasswordResetRequestDto::class.java)
            .flatMap { (_, userName, oneTimePassword) -> service.validateOTP(oneTimePassword, userName) }
            .flatMap { dto: String -> ServerResponse.status(HttpStatus.OK).bodyValue(dto) }
    }

}