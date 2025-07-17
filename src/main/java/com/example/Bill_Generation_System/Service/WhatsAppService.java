package com.example.Bill_Generation_System.Service;
import com.example.Bill_Generation_System.DTO.TwilioConfig;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Service;


@Service
public class WhatsAppService {


    private final TwilioConfig twilioConfig;

    public WhatsAppService(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
    }

    @PostConstruct
    public void initTwilio() {
        Twilio.init(twilioConfig.getAccountSid(),twilioConfig.getAuthToken());
    }

    public void sendMessage(String to, String body) {
        try{
            Message message = Message.creator(
                    new com.twilio.type.PhoneNumber("whatsapp:" + to),
                    new com.twilio.type.PhoneNumber("whatsapp:"+twilioConfig.getPhoneNumber()),
                    body
            ).create();

            System.out.println("Message sent! SID: " + message.getSid());
        }catch (Exception e){
            throw new RuntimeException("Failed to send message: " + e.getMessage());
        }

    }
}
