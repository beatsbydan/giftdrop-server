package com.tobipeter.giftdrop.db.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Table(name="windows")
@Entity
@Getter
@Setter
public class Window extends BaseModel{
    private String code;

    private boolean active;

    private LocalDate startDate;

    private LocalDate endDate;

    private boolean isWishing;

    private boolean isGifting;

    private Long netWishLists;

    private Long activeGifters;

    private Long completedGifts; // SAME AS GRANTED WISHES

    private Integer percentGiftingCompleted;

    private Long totalGifters; // SAME AS ACTIVE GIFTERS

    private Long totalWishers; //SAME AS NET WISHLISTS

    private Long totalExpenses;

    private Long totalServiceFee;

    private Long totalProductsFee;

    private boolean isNextWindow;

    @OneToMany(mappedBy = "window")
    private List<ExpenseRecord> expenseRecords;

    public String generateCode(){
        return UUID.randomUUID().toString().substring(0, 3);
    }

}
