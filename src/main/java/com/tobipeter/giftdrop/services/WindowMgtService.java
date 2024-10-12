package com.tobipeter.giftdrop.services;

import com.tobipeter.giftdrop.db.models.Window;
import com.tobipeter.giftdrop.db.services.window.WindowService;
import com.tobipeter.giftdrop.dtos.request.window.CreateWindow;
import com.tobipeter.giftdrop.dtos.response.MessageResponse;
import com.tobipeter.giftdrop.dtos.response.window.RecentWindowResponse;
import com.tobipeter.giftdrop.dtos.response.window.WindowExpensesResponse;
import com.tobipeter.giftdrop.dtos.response.window.WindowResponse;
import com.tobipeter.giftdrop.exceptions.DuplicateEntryException;
import com.tobipeter.giftdrop.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WindowMgtService {
    private final WindowService windowService;

    public MessageResponse createWindow(CreateWindow request) throws DuplicateEntryException{
        boolean exists = windowService.isExists(request);
        boolean hasNextWindow = windowService.hasNextWindow();

        if(exists){
            throw new DuplicateEntryException("Window already exists.");
        }
        else if(hasNextWindow){
            throw new DuplicateEntryException("Next window has already been created.");
        }

        windowService.save(toDbModel(request));

        return new MessageResponse("Next window created successfully.");
    }

    public WindowResponse getNextWindow() throws NotFoundException {
        Window window = windowService.getNextWindow();

        return toResponse(window, null, null);
    }

    public Page<WindowExpensesResponse> getAllExpensesForAllWindows(int page, int size){
        Pageable pageable = PageRequest.of(page, size);

        return toPaginatedResponse(windowService.getAllWindows(pageable));

    }

    public WindowResponse getActiveWindow() throws NotFoundException{
        Window window = windowService.getActiveWindow();
        Window mostRecentWindow = windowService.getById(window.getId() - 1);
        Window secondMostRecentWindow = windowService.getById(window.getId() - 2);

        return toResponse(window, mostRecentWindow, secondMostRecentWindow);
    }

    public void activateWindow(String code) throws NotFoundException{
        windowService.activateWindow(code);
    }

    public void deactivateWindow(){
        windowService.deactivateWindow();
    }

    public MessageResponse updateWindowStatus(String code, boolean isWishing, boolean isGifting) throws NotFoundException{
        windowService.changeWindowStatus(code, isWishing, isGifting);

        String WindowType = isWishing ? "Wishing" : isGifting ? "Gifting" : null;

        return new MessageResponse(WindowType +" window was successfully closed ");
    }

    private WindowResponse toResponse(Window window, Window mostRecentWindow, Window secondMostRecentWindow){
        WindowResponse response = new WindowResponse();

        response.setCode(window.getCode());
        response.setActive(window.isActive());
        response.setStartDate(window.getStartDate());
        response.setEndDate(window.getEndDate());
        response.setWishing(window.isWishing());
        response.setGifting(window.isGifting());
        response.setNetWishLists(window.getNetWishLists());
        response.setActiveGifters(window.getActiveGifters());
        response.setCompletedGifts(window.getCompletedGifts());
        response.setPercentGiftingCompleted(window.getPercentGiftingCompleted());
        response.setGiftersWishersRatio(generateRatio(window.getActiveGifters(), window.getTotalWishers()));
        response.setTotalExpenses(window.getTotalExpenses());
        response.setTotalServiceFee(window.getTotalServiceFee());
        response.setTotalProductsFee(window.getTotalProductsFee());
        response.setNextWindow(window.isNextWindow());

        if(mostRecentWindow != null && secondMostRecentWindow != null){
            RecentWindowResponse recentWindowResponse = getRecentWindowResponse(mostRecentWindow);
            response.setRecentWindow(recentWindowResponse);

            List<RecentWindowResponse> pastTwoWindowsResponse = new ArrayList<>();
            pastTwoWindowsResponse.add(recentWindowResponse);
            pastTwoWindowsResponse.add(getRecentWindowResponse(secondMostRecentWindow));
            response.setPastTwoWindows(pastTwoWindowsResponse);
        }

        return response;
    }

    private Page<WindowExpensesResponse> toPaginatedResponse(Page<Window> windows){
        List<WindowExpensesResponse> windowsList = new ArrayList<>();

        for(Window window: windows.getContent()){
            WindowExpensesResponse expensesResponse = new WindowExpensesResponse();

            expensesResponse.setTotalExpenses(window.getTotalExpenses());
            expensesResponse.setTotalServiceFee(window.getTotalServiceFee());
            expensesResponse.setTotalProductsFee(window.getTotalProductsFee());
            expensesResponse.setDate(window.getStartDate());
            windowsList.add(expensesResponse);
        }

        return new PageImpl<>(windowsList, windows.getPageable(), windows.getTotalElements());
    }

    private RecentWindowResponse getRecentWindowResponse(Window recentWindow){
        RecentWindowResponse recentWindowResponse = new RecentWindowResponse();

        recentWindowResponse.setTotalAmountSpent(recentWindow.getTotalExpenses());
        recentWindowResponse.setWishesGranted(recentWindow.getCompletedGifts());
        recentWindowResponse.setStartDate(recentWindow.getStartDate());
        recentWindowResponse.setTotalGifters(recentWindow.getTotalGifters());
        recentWindowResponse.setTotalWishers(recentWindow.getTotalWishers());

        return recentWindowResponse;
    }

    private Window toDbModel(CreateWindow request){
        Window window = new Window();

        window.setCode(window.generateCode());
        window.setStartDate(LocalDate.parse(request.getStartDate(), DateTimeFormatter.ISO_LOCAL_DATE));
        window.setEndDate(LocalDate.parse(request.getEndDate(), DateTimeFormatter.ISO_LOCAL_DATE));
        window.setGifting(false);
        window.setWishing(false);

        return window;
    }

    private String generateRatio (Long gifters, Long wishers) {
        String ratio = null;

        /**
         * TODO: Algorithm to generate ratio
         * --> Pseudo-Code.
         * 1.
         */

        Float ratioFloat = gifters.floatValue() / wishers.floatValue();

        return ratio;
    }
}
