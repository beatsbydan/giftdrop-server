package com.tobipeter.giftdrop.services;

import com.tobipeter.giftdrop.db.models.auth.GiftDropUser;
import com.tobipeter.giftdrop.db.services.auth.user.UserService;
import com.tobipeter.giftdrop.dtos.request.auth.UpdateUser;
import com.tobipeter.giftdrop.dtos.response.user.UserResponse;
import com.tobipeter.giftdrop.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserMgtService {
    private final UserService userService;

    public UserResponse updateUser(UpdateUser request, String code) throws NotFoundException {
        GiftDropUser existingGiftDropUser = userService.getByCode(code);

        updateDbModel(request, existingGiftDropUser);

        return toResponse(userService.save(existingGiftDropUser));
    }

    public List<UserResponse> getRankedUsers(){
        Pageable pageable = PageRequest.of(0, 10);
        return toListResponse(userService.getRankedUsers(pageable));
    }
    private void updateDbModel(UpdateUser request, GiftDropUser existingUser){
        if(request.getAddress() != null){
            existingUser.setAddress(request.getAddress());
        }
        if(request.getBio() != null){
            existingUser.setBio(request.getBio());
        }
        if(request.getPhone() != null){
            existingUser.setPhone(request.getPhone());
        }
    }

    private UserResponse toResponse(GiftDropUser user){
        UserResponse response = new UserResponse();

        response.setWishingId(user.getWishingId());
        response.setGiftingId(user.getGiftingId());
        response.setFirstName(user.getFirstName());
        response.setUserName(user.getUsername());
        response.setEmail(user.getEmail());
        response.setAddress(user.getAddress());
        response.setBio(user.getBio());
        response.setRole(user.getRole().name());

        return response;
    }

    private List<UserResponse> toListResponse(List<GiftDropUser> users){
        List<UserResponse> responses = new ArrayList<>();
        for(GiftDropUser user : users){
            responses.add(toResponse(user));
        }

        return responses;
    }
}
