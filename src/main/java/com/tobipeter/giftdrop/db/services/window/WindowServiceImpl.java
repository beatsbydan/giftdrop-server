package com.tobipeter.giftdrop.db.services.window;

import com.tobipeter.giftdrop.db.models.Window;
import com.tobipeter.giftdrop.db.repositories.WindowRepository;
import com.tobipeter.giftdrop.dtos.request.window.CreateWindow;
import com.tobipeter.giftdrop.exceptions.DuplicateEntryException;
import com.tobipeter.giftdrop.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class WindowServiceImpl implements WindowService{
    private final WindowRepository repository;

    @Override
    public Window save(Window window) throws DuplicateEntryException {
        return repository.save(window);
    }

    @Override
    public Page<Window> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Window getNextWindow() throws NotFoundException {
        return repository.getNextWindow()
                .orElseThrow(() -> new NotFoundException("Window was not found."));
    }

    @Override
    public Window getByCode (String code) throws NotFoundException {
        return repository.findByCode(code)
                .orElseThrow(() -> new NotFoundException("Window was not found"));
    }

    @Override
    public Window getById(Long id) {
        return repository.getReferenceById(id);
    }

    @Override
    public void activateWindow(String code) throws NotFoundException {
        getByCode(code);
        repository.activate(code);
    }

    @Override
    public void deactivateWindow(){
        repository.deactivate();
    }

    @Override
    public Window getActiveWindow() throws NotFoundException {
        return repository.getCurrentWindow()
                .orElseThrow(()->new NotFoundException("No active window present"));
    }

    @Override
    public boolean isExists(CreateWindow request) {
        LocalDate start = LocalDate.parse(request.getStartDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate end = LocalDate.parse(request.getEndDate(), DateTimeFormatter.ISO_LOCAL_DATE);

        return repository.existsByPeriod(start, end).isPresent();
    }

    @Override
    public boolean hasNextWindow(){
        return repository.getNextWindow().isPresent();
    }

    @Override
    public int updateNetWishListsInCurrentWindow(String code, Long currentCount) throws NotFoundException {
        return repository.updateNetWishLists(code, currentCount);
    }

    @Override
    public int updateActiveGiftersInCurrentWindow(String code, Long currentCount) throws NotFoundException {
        return repository.updateActiveGifters(code, currentCount);
    }

    @Override
    public int updateCompletedGiftsInCurrentWindow(String code, Long currentCount) throws NotFoundException {
        return repository.updateCompletedGifts(code, currentCount);
    }

    @Override
    public int updatePercentGiftingCompletedInCurrentWindow(String code, Integer currentCount) throws NotFoundException {
        return repository.updatePercentGiftingCompleted(code, currentCount);
    }

    @Override
    public int updateTotalGiftersInCurrentWindow(String code, Long currentCount) throws NotFoundException {
        return repository.updateTotalGifters(code, currentCount);
    }

    @Override
    public int updateTotalWishersInCurrentWindow(String code, Long currentCount) throws NotFoundException {
        return repository.updateTotalWishers(code, currentCount);
    }

    @Override
    public int changeWindowStatus(String code, boolean isWishing, boolean isGifting) throws NotFoundException {
        return repository.changeStatus(code, isWishing, isGifting);
    }

    @Override
    public Page<Window> getAllWindows(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
