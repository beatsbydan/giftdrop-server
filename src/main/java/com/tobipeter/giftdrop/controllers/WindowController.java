package com.tobipeter.giftdrop.controllers;

import com.tobipeter.giftdrop.dtos.request.window.CreateWindowDto;
import com.tobipeter.giftdrop.dtos.response.MessageResponse;
import com.tobipeter.giftdrop.dtos.response.window.WindowExpensesResponse;
import com.tobipeter.giftdrop.dtos.response.window.WindowResponseDto;
import com.tobipeter.giftdrop.exceptions.DuplicateEntryException;
import com.tobipeter.giftdrop.exceptions.NotFoundException;
import com.tobipeter.giftdrop.services.WindowMgtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/windows")
@RestController
@Validated
@RequiredArgsConstructor
public class WindowController {
    private final WindowMgtService mgtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponse createWindow(
            @Valid @RequestBody CreateWindowDto request
    ) throws DuplicateEntryException {
        return mgtService.createWindow(request);
    }

    @GetMapping("/past-expenses")
    public Page<WindowExpensesResponse> getExpenses(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ){
        return mgtService.getAllExpensesForAllWindows(page, size);
    }

    @GetMapping("/next")
    public WindowResponseDto getCurrentWindow() throws NotFoundException {
        return mgtService.getNextWindow();
    }

    @GetMapping("/active")
    public WindowResponseDto getActiveWindow() throws NotFoundException{
        return mgtService.getActiveWindow();
    }

    @PutMapping("/deactivate")
    public void deactivate(){
        mgtService.deactivateWindow();
    }


    @PutMapping("/toggle/{code}")
    public MessageResponse updateStatus (
            @PathVariable String code,
            @RequestParam(name = "isWishing") boolean isWishing,
            @RequestParam(name = "isGifting") boolean isGifting
    ) throws NotFoundException {
        return mgtService.updateWindowStatus(code, isWishing, isGifting);
    }
}
