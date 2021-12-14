package ru.grobikon.otpdemo.dto

data class PasswordResetRequestDto(
    var phoneNumber: String,
    var userName: String,
    var oneTimePassword: String? = null
)