package ru.grobikon.otpdemo.service

import com.twilio.rest.api.v2010.account.Message
import com.twilio.type.PhoneNumber
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.grobikon.otpdemo.config.TwilioConfig
import ru.grobikon.otpdemo.dto.OtpStatus
import ru.grobikon.otpdemo.dto.PasswordResetRequestDto
import ru.grobikon.otpdemo.dto.PasswordResetResponseDto
import java.text.DecimalFormat
import kotlin.random.Random


@Service
class TwilioOTPService(
    val twilioConfig: TwilioConfig
) {

    var otpMap: MutableMap<String, String> = mutableMapOf()

    fun sendOTPForPasswordReset(passwordResetRequestDto: PasswordResetRequestDto): Mono<PasswordResetResponseDto> {
        val passwordResetResponseDto: PasswordResetResponseDto = try {
            val to = PhoneNumber(passwordResetRequestDto.phoneNumber)
            val from = PhoneNumber(twilioConfig.trialNumber)
            val otp = generateOTP()
            val otpMessage = "Ваш OTP: $otp. Используй этот код для выполнения вашей транзакции. Спасибо."
            val message: Message = Message.creator(to, from, otpMessage).create()
            otpMap[passwordResetRequestDto.userName] = otp
            PasswordResetResponseDto(OtpStatus.DELIVERED, otpMessage)
        } catch (e: Exception) {
            PasswordResetResponseDto(OtpStatus.FAILED, e.message.toString())
        }
        return Mono.just(passwordResetResponseDto)
    }

    fun validateOTP(userInputOtp: String?, userName: String): Mono<String> {
        if (userInputOtp.equals(otpMap[userName])) {
            return Mono.just("OTP совпадает продолжай свю транзакцию.")
        } else {
            return Mono.error(IllegalArgumentException("Ошибка OTP. Повторите логику."))
        }

    }

    fun generateOTP(): String {
        return DecimalFormat("000000").format(Random.nextInt(999999))
    }
}