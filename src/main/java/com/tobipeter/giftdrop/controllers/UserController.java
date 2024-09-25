package com.tobipeter.giftdrop.controllers;

import com.tobipeter.giftdrop.dtos.request.auth.UpdateUser;
import com.tobipeter.giftdrop.dtos.response.user.UserResponse;
import com.tobipeter.giftdrop.exceptions.NotFoundException;
import com.tobipeter.giftdrop.services.UserMgtService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserMgtService mgtService;

    @PutMapping("{code}")
    public UserResponse updateUSer(
            @RequestBody UpdateUser request,
            @PathVariable String code
    ) throws NotFoundException {
        return mgtService.updateUser(request, code);
    }

    @GetMapping("/rankings")
    public List<UserResponse> getRankedUsers(){
        return mgtService.getRankedUsers();
    }
}
