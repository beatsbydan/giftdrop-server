package com.tobipeter.giftdrop.db.services.window;

import com.tobipeter.giftdrop.db.models.Window;
import com.tobipeter.giftdrop.dtos.request.window.CreateWindow;
import com.tobipeter.giftdrop.exceptions.DuplicateEntryException;
import com.tobipeter.giftdrop.exceptions.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WindowService {
    Window save(Window window) throws DuplicateEntryException;
    Page<Window> findAll(Pageable pageable);
    Window getNextWindow() throws NotFoundException;
    Window getActiveWindow() throws NotFoundException;
    Window getByCode(String code) throws NotFoundException;
    Window getById(Long id) throws NotFoundException;
    void activateWindow(String code) throws NotFoundException;
    void deactivateWindow();
    boolean isExists(CreateWindow request);
    boolean hasNextWindow();
    int updateNetWishListsInCurrentWindow(String code, Long currentCount) throws NotFoundException;
    int updateActiveGiftersInCurrentWindow(String code, Long currentCount) throws NotFoundException;
    int updateCompletedGiftsInCurrentWindow(String code, Long currentCount) throws NotFoundException;
    int updatePercentGiftingCompletedInCurrentWindow(String code, Integer currentCount) throws NotFoundException;
    int updateTotalGiftersInCurrentWindow(String code, Long currentCount) throws NotFoundException;
    int updateTotalWishersInCurrentWindow(String code, Long currentCount) throws NotFoundException;
    int changeWindowStatus(String code, boolean isWishing, boolean isGifting) throws NotFoundException;
    Page<Window> getAllWindows(Pageable pageable);
}
