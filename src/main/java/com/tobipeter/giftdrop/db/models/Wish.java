package com.tobipeter.giftdrop.db.models;

import com.tobipeter.giftdrop.db.models.auth.GiftDropUser;
import com.tobipeter.giftdrop.enums.Category;
import com.tobipeter.giftdrop.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "wishes")
public class Wish extends BaseModel {
    private String code;

    private String name;

    private Long price;

    private String link;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "wishingId")
    private GiftDropUser giftDropUser;

    @Enumerated(EnumType.STRING)
    private Status status;

    public String generateCode(){
        return UUID.randomUUID().toString().substring(0, 3);
    }

}
