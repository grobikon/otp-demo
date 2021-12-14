package ru.grobikon.otpdemo.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "twilio")
class TwilioConfig(
) {

    lateinit var accountSid: String
    lateinit var authToken: String
    lateinit var trialNumber: String
}