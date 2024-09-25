package com.tobipeter.giftdrop.services;

import com.tobipeter.giftdrop.db.models.auth.GiftDropUser;
import com.tobipeter.giftdrop.db.models.Wish;
import com.tobipeter.giftdrop.db.services.auth.user.UserService;
import com.tobipeter.giftdrop.db.services.wish.WishService;
import com.tobipeter.giftdrop.dtos.request.wish.CreateWishDto;
import com.tobipeter.giftdrop.dtos.request.wish.CreateWishListDto;
import com.tobipeter.giftdrop.dtos.request.wish.UpdateWishDto;
import com.tobipeter.giftdrop.dtos.request.wish.UpdateWishListDto;
import com.tobipeter.giftdrop.dtos.response.wish.ShareWishResponse;
import com.tobipeter.giftdrop.dtos.response.wish.UserWishesResponseDto;
import com.tobipeter.giftdrop.dtos.response.wish.WishResponseDto;
import com.tobipeter.giftdrop.enums.Category;
import com.tobipeter.giftdrop.enums.Status;
import com.tobipeter.giftdrop.exceptions.DuplicateEntryException;
import com.tobipeter.giftdrop.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class WishMgtService {
    private final WishService wishService;
    private final UserService userService;

    public List<WishResponseDto> createWishList(CreateWishListDto request) throws NotFoundException, DuplicateEntryException {
        String userWishingId = request.getWishes()
                .stream()
                .findFirst().map(CreateWishDto::getUserWishingId).orElse(null);

        GiftDropUser user = userService.getByWishingId(userWishingId);
        user.setHasWish(true);
        userService.save(user);

        return toListResponse(wishService.save(toListWishDBModel(request.getWishes(), user)));
    }

    public List<UserWishesResponseDto> getAllWishes(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<GiftDropUser> requestedUsers = userService.getPaginatedUsers(pageable);

        List<UserWishesResponseDto> requestedWishes= new ArrayList<>();

        for(GiftDropUser user : requestedUsers.getContent()){
            List<WishResponseDto> userWishes = getWishesByWishingId(user.getWishingId());
            addToResponse(requestedWishes, userWishes, user);
        }

        return requestedWishes;
    }

    public ShareWishResponse shareWishes(String wishingId) throws NotFoundException {
        GiftDropUser user = userService.getByWishingId(wishingId);
        List<WishResponseDto> userWishes = getWishesByWishingId(wishingId);

        return new ShareWishResponse(user.getBio(), userWishes);
    }

    public List<WishResponseDto> getWishesByWishingId(String wishingId){
        return toListResponse(wishService.getWishesById(wishingId));
    }

    public void updateWishItems(UpdateWishListDto request) throws NotFoundException{
        for(UpdateWishDto wish : request.getWishes()){
           Wish currentWish = wishService.findByCode(wish.getCode());
           currentWish.setStatus(Status.valueOf(wish.getStatus()));
           wishService.save(currentWish);
        }
    }

    private Wish toWishDBModel(CreateWishDto request, GiftDropUser user){
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

    private WishResponseDto toResponse(Wish wish){
        WishResponseDto response = new WishResponseDto();

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

    private List<Wish> toListWishDBModel(List<CreateWishDto> wishRequests, GiftDropUser user) throws DuplicateEntryException {
        List<Wish> newWishList= new ArrayList<>();

        for(CreateWishDto wishRequest : wishRequests){
            Optional<Wish> wishByName = wishService.findByName(wishRequest.getName());
            if(wishByName.isPresent()){
                throw new DuplicateEntryException("Wish already exists");
            }
            newWishList.add(toWishDBModel(wishRequest, user));
        }

        return newWishList;
    }

    private List<WishResponseDto> toListResponse(List<Wish> wishes){
        List<WishResponseDto> newWishListResponse= new ArrayList<>();

        for(Wish wish : wishes){
            newWishListResponse.add(toResponse(wish));
        }

        return newWishListResponse;
    }

    private void addToResponse(List<UserWishesResponseDto> wishes, List<WishResponseDto> userWishes, GiftDropUser user){
        UserWishesResponseDto currentUserWish = new UserWishesResponseDto();

        currentUserWish.setUserName(user.getUsername());
        currentUserWish.setWishingId(user.getWishingId());
        currentUserWish.setUserWishes(userWishes);

        wishes.add(currentUserWish);

    }
}
