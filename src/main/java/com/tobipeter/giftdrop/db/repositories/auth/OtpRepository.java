package com.tobipeter.giftdrop.db.repositories.auth;

import com.tobipeter.giftdrop.db.models.auth.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, Long> {
    Optional<Otp> findByOtp(String otp);
}
