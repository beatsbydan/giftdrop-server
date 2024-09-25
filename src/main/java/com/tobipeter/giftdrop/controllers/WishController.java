package com.tobipeter.giftdrop.controllers;

import com.tobipeter.giftdrop.dtos.request.wish.CreateWishListDto;
import com.tobipeter.giftdrop.dtos.request.wish.UpdateWishListDto;
import com.tobipeter.giftdrop.dtos.response.wish.ShareWishResponse;
import com.tobipeter.giftdrop.dtos.response.wish.UserWishesResponseDto;
import com.tobipeter.giftdrop.dtos.response.wish.WishResponseDto;
import com.tobipeter.giftdrop.exceptions.DuplicateEntryException;
import com.tobipeter.giftdrop.exceptions.NotFoundException;
import com.tobipeter.giftdrop.services.WishMgtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class WishController {
    private final WishMgtService wishMgtService;

    @PostMapping("/users/wishes")
    @ResponseStatus(HttpStatus.CREATED)
    public List<WishResponseDto> createWishes(
            @Valid @RequestBody CreateWishListDto request
    ) throws NotFoundException, DuplicateEntryException {
        return wishMgtService.createWishList(request);
    }

    @GetMapping("/users/wishes")
    public List<UserWishesResponseDto> getPaginatedWishes(
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size
    ){
        return wishMgtService.getAllWishes(page, size);
    }

    @GetMapping("/users/wishes/{wishingId}")
    public List<WishResponseDto> getWishListByWishingId(
            @PathVariable String wishingId
    ){
        return wishMgtService.getWishesByWishingId(wishingId);
    }

    @GetMapping("/wish/share/{wishingId}")
    public ShareWishResponse shareWishByWishingId(
            @PathVariable String wishingId
    ) throws NotFoundException {
        return wishMgtService.shareWishes(wishingId);
    }

    @PutMapping("/users/wishes")
    public void updateWish(
            @Valid @RequestBody UpdateWishListDto request
            ) throws NotFoundException {
        wishMgtService.updateWishItems(request);
    }
}