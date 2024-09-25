package com.tobipeter.giftdrop.services.auth;

import com.tobipeter.giftdrop.db.models.auth.Otp;
import com.tobipeter.giftdrop.db.services.auth.otp.OtpService;
import com.tobipeter.giftdrop.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpMgtService {
    private final OtpService otpService;

    public Otp save(Otp otp){
        return otpService.save(otp);
    }

    public String createOtp(){
        Random random = new Random();

        int randomValue = random.nextInt(9000) + 1000;

        return String.valueOf(randomValue);
    }

    public boolean isValid(String otpValue) throws NotFoundException {
        Otp otp = otpService.findByOtp(otpValue);

        return Duration.between(otp.getCreatedAt(), LocalDateTime.now()).getSeconds() < otp.getExpiration();
    }
}
