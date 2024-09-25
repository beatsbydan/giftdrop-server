package com.tobipeter.giftdrop.dtos.request.wish;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CreateWishListDto {
    @Size(min = 1, message = "At least one wish must be present")
    private List<CreateWishDto> wishes;
}
