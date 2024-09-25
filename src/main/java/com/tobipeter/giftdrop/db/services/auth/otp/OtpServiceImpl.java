package com.tobipeter.giftdrop.db.services.auth.otp;

import com.tobipeter.giftdrop.db.models.auth.Otp;
import com.tobipeter.giftdrop.db.repositories.auth.OtpRepository;
import com.tobipeter.giftdrop.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {
    private final OtpRepository repo;

    @Override
    public Otp save(Otp otp) {
        return repo.save(otp);
    }

    @Override
    public Otp findByOtp(String otp) throws NotFoundException {
        return repo.findByOtp(otp)
                .orElseThrow(()-> new NotFoundException("Invalid OTP."));
    }
}
