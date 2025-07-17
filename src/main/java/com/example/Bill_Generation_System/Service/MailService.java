package com.example.Bill_Generation_System.Service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;


@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendWithCsvAttachment(String to, String subject,String body,String csvPath){

        MimeMessage message = javaMailSender.createMimeMessage();

        try{
            MimeMessageHelper helper = new MimeMessageHelper(message,true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);

            FileSystemResource  file = new FileSystemResource(new File(csvPath));
            helper.addAttachment(file.getFilename(),file);

            javaMailSender.send(message);


        }catch (Exception e){
            throw new RuntimeException("Failed to send email: " + e.getMessage(), e);
        }

    }


}
