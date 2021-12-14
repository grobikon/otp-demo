package ru.grobikon.otpdemo

import com.twilio.Twilio
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import ru.grobikon.otpdemo.config.TwilioConfig
import javax.annotation.PostConstruct

@SpringBootApplication
class OtpDemoApplication(
    val twilioConfig: TwilioConfig
){

    @PostConstruct
    fun initTwilio(){
        Twilio.init(twilioConfig.accountSid, twilioConfig.authToken)
    }
}

fun main(args: Array<String>) {
    runApplication<OtpDemoApplication>(*args)
}
