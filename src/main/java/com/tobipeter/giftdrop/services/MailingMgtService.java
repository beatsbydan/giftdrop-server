package com.tobipeter.giftdrop.services;

import com.tobipeter.giftdrop.db.models.auth.GiftDropUser;
import com.tobipeter.giftdrop.exceptions.MailingException;
import com.tobipeter.giftdrop.services.auth.OtpMgtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class MailingMgtService {
    private final OtpMgtService otpMgtService;
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public String sendOtpToUser(GiftDropUser user) throws MailingException {
        String otp = otpMgtService.createOtp();

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setSubject("Email Verification");
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setText(String.format("Hello %s, please use this OTP: %s", user.getFirstName(), otp));

        try{
            javaMailSender.send(simpleMailMessage);
        }
        catch(MailException ex){
            log.warn(ex.getMessage());
            throw new MailingException("Unable to send mail.");
        }

        return otp;
    }
}
