package com.tobipeter.giftdrop.db.services.auth.otp;

import com.tobipeter.giftdrop.db.models.auth.Otp;
import com.tobipeter.giftdrop.exceptions.NotFoundException;

public interface OtpService {
    Otp save(Otp otp);

    Otp findByOtp(String otp) throws NotFoundException;
}
