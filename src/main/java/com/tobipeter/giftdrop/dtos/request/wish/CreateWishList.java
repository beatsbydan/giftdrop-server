package com.tobipeter.giftdrop.dtos.request.wish;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CreateWishList {
    @Size(min = 1, message = "At least one wish must be present")
    private List<CreateWish> wishes;

    @NotBlank(message = "Address cannot be blank.")
    private String address;

    @NotBlank(message = "Phone cannot be blank.")
    private String phone;

    @NotBlank(message = "User ID cannot be blank.")
    private String userWishingId;
}
