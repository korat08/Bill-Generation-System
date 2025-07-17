package com.example.Bill_Generation_System.Service;


import com.example.Bill_Generation_System.DTO.TwilioConfig;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    private final TwilioConfig twilioConfig;

    public SmsService(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
    }

    @PostConstruct
    public void initTwilio() {
        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
    }

    public void sendSms(String to, String body){
        try{
            Message message =  Message.creator(
                    new PhoneNumber(to),
                    new PhoneNumber(twilioConfig.getPhoneNumberSms()),
                    body
            ).create();
        }catch (Exception e){
            throw new RuntimeException("Failed to send SMS: " + e.getMessage());
        }

    }

}
