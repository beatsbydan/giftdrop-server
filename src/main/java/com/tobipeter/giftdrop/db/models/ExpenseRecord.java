package com.tobipeter.giftdrop.db.models;

import com.tobipeter.giftdrop.db.models.auth.GiftDropUser;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Table(name = "expense_records")
@Entity
@EqualsAndHashCode(callSuper = false)
public class ExpenseRecord extends BaseModel{
    private long deliveryFee;

    private long serviceFee;

    private long productsFee;

    private long total;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private GiftDropUser giftDropUser;

    @ManyToOne
    @JoinColumn(name = "window_id", referencedColumnName = "id")
    private Window window;
}
