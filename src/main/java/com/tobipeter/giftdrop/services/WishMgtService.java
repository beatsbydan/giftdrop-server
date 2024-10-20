package com.tobipeter.giftdrop.services;

import com.tobipeter.giftdrop.db.models.auth.GiftDropUser;
import com.tobipeter.giftdrop.db.models.Wish;
import com.tobipeter.giftdrop.db.services.auth.user.UserService;
import com.tobipeter.giftdrop.db.services.wish.WishService;
import com.tobipeter.giftdrop.dtos.request.wish.CreateWish;
import com.tobipeter.giftdrop.dtos.request.wish.CreateWishList;
import com.tobipeter.giftdrop.dtos.request.wish.UpdateWish;
import com.tobipeter.giftdrop.dtos.request.wish.UpdateWishList;
import com.tobipeter.giftdrop.dtos.response.wish.ShareWishResponse;
import com.tobipeter.giftdrop.dtos.response.wish.UserWishesResponse;
import com.tobipeter.giftdrop.dtos.response.wish.WishResponse;
import com.tobipeter.giftdrop.enums.Category;
import com.tobipeter.giftdrop.enums.Status;
import com.tobipeter.giftdrop.exceptions.DuplicateEntryException;
import com.tobipeter.giftdrop.exceptions.NotFoundException;
import com.tobipeter.giftdrop.exceptions.RequestValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class WishMgtService {
    private final WishService wishService;
    private final UserService userService;

    public List<WishResponse> createWishList(CreateWishList request) throws NotFoundException, RequestValidationException {
        GiftDropUser user = userService.getByWishingId(request.getUserWishingId());
        if(user.isHasWish()){
            throw new RequestValidationException("You have already made a wish.");
        }

        user.setHasWish(true);
        user.setAddress(request.getAddress());
        user.setPhone(request.getPhone());
        userService.save(user);

        return toListResponse(wishService.save(toListWishDBModel(request.getWishes(), user)));
    }

    public List<UserWishesResponse> getAllWishes(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<GiftDropUser> requestedUsers = userService.getPaginatedUsers(pageable);

        List<UserWishesResponse> requestedWishes= new ArrayList<>();

        for(GiftDropUser user : requestedUsers.getContent()){
            List<WishResponse> userWishes = getWishesByWishingId(user.getWishingId());
            addToResponse(requestedWishes, userWishes, user);
        }

        return requestedWishes;
    }

    public ShareWishResponse shareWishes(String wishingId) throws NotFoundException {
        GiftDropUser user = userService.getByWishingId(wishingId);
        List<WishResponse> userWishes = getWishesByWishingId(wishingId);

        return new ShareWishResponse(user.getBio(), userWishes);
    }

    public List<WishResponse> getWishesByWishingId(String wishingId){
        return toListResponse(wishService.getWishesById(wishingId));
    }

    public void updateWishItems(UpdateWishList request) throws NotFoundException{
        for(UpdateWish wish : request.getWishes()){
           Wish currentWish = wishService.findByCode(wish.getCode());
           currentWish.setStatus(Status.valueOf(wish.getStatus()));
           wishService.save(currentWish);
        }
    }

    private Wish toWishDBModel(CreateWish request, GiftDropUser user){
        Wish newWish = new Wish();

        newWish.setCode(newWish.generateCode());
        newWish.setName(request.getName());
        newWish.setLink(request.getLink());
        newWish.setPrice(request.getPrice());
        newWish.setCategory(Category.valueOf(request.getCategory()));
        newWish.setStatus(Status.PENDING);
        newWish.setGiftDropUser(user);

        return newWish;
    }

    private WishResponse toResponse(Wish wish){
        WishResponse response = new WishResponse();

        response.setWishCategory(wish.getCategory().name());
        response.setWishCode(wish.getCode());
        response.setWishName(wish.getName());
        response.setWishLink(wish.getLink());
        response.setWishPrice(wish.getPrice());
        response.setWishStatus(wish.getStatus().name());
        response.setWishingId(wish.getGiftDropUser().getWishingId());
        response.setUserName(wish.getGiftDropUser().getUsername());

        return response;
    }

    private List<Wish> toListWishDBModel(List<CreateWish> wishRequests, GiftDropUser user) {
        List<Wish> newWishList= new ArrayList<>();

        for(CreateWish wishRequest : wishRequests){
            newWishList.add(toWishDBModel(wishRequest, user));
        }

        return newWishList;
    }

    private List<WishResponse> toListResponse(List<Wish> wishes){
        List<WishResponse> newWishListResponse= new ArrayList<>();

        for(Wish wish : wishes){
            newWishListResponse.add(toResponse(wish));
        }

        return newWishListResponse;
    }

    private void addToResponse(List<UserWishesResponse> wishes, List<WishResponse> userWishes, GiftDropUser user){
        UserWishesResponse currentUserWish = new UserWishesResponse();

        currentUserWish.setUserName(user.getUsername());
        currentUserWish.setWishingId(user.getWishingId());
        currentUserWish.setUserWishes(userWishes);

        wishes.add(currentUserWish);

    }
}
