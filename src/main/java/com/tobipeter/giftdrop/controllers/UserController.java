package com.tobipeter.giftdrop.controllers;

import com.tobipeter.giftdrop.dtos.request.auth.CreateUserDto;
import com.tobipeter.giftdrop.dtos.request.auth.UpdateUserDto;
import com.tobipeter.giftdrop.dtos.response.user.UserResponseDto;
import com.tobipeter.giftdrop.exceptions.DuplicateEntryException;
import com.tobipeter.giftdrop.exceptions.NotFoundException;
import com.tobipeter.giftdrop.services.UserMgtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
    public UserResponseDto updateUSer(
            @RequestBody UpdateUserDto request,
            @PathVariable String code
    ) throws NotFoundException {
        return mgtService.updateUser(request, code);
    }

    @GetMapping("/rankings")
    public List<UserResponseDto> getRankedUsers(){
        return mgtService.getRankedUsers();
    }
}
