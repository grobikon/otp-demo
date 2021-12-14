package ru.grobikon.otpdemo.dto

data class PasswordResetResponseDto(
    var status: OtpStatus,
    val message: String
)