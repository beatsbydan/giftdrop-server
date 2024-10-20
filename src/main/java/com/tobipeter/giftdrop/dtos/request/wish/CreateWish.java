package com.tobipeter.giftdrop.dtos.request.wish;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateWish {
    @NotBlank(message = "Wish name cannot be blank.")
    private String name;

    @NotBlank(message = "Category cannot be blank.")
    private String category;

    @NotBlank(message = "Price cannot be blank.")
    private Long price;

    @NotBlank(message = "String cannot be blank.")
    private String link;

}
