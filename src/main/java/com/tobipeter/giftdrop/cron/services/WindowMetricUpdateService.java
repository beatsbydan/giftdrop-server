package com.tobipeter.giftdrop.cron.services;

import com.tobipeter.giftdrop.db.services.auth.user.UserService;
import com.tobipeter.giftdrop.db.services.window.WindowService;
import com.tobipeter.giftdrop.db.services.wish.WishService;
import com.tobipeter.giftdrop.exceptions.WindowMetricUpdateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WindowMetricUpdateService {
    private final UserService userService;
    private final WishService wishService;
    private final WindowService windowService;

    /**
     * TODO:
     *  ->
     */

    public void update() throws WindowMetricUpdateException{

    }
}
