package com.tobipeter.giftdrop.db.models;

import com.tobipeter.giftdrop.db.models.auth.GiftDropUser;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Random;

@Table(name = "invoices")
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Invoice extends BaseModel{
    private String name;

    private Long amountSpent;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private GiftDropUser giftDropUser;

    @OneToOne
    @JoinColumn(name = "expense_record_code", referencedColumnName = "id")
    private ExpenseRecord expenseRecord;

    public String generateName(){
        Random random = new Random();

        int randomValue = random.nextInt(9000);

        return "INV0000" + randomValue;
    }
}
