package com.tobipeter.giftdrop.db.models.auth;

import com.tobipeter.giftdrop.db.models.BaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Table(name = "otps")
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Otp extends BaseModel {
    private String otp;

    private long expiration = 900;
}
