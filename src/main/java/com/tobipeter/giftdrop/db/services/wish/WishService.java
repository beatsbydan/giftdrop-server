package com.tobipeter.giftdrop.db.services.wish;

import com.tobipeter.giftdrop.db.models.Wish;
import com.tobipeter.giftdrop.enums.Status;
import com.tobipeter.giftdrop.exceptions.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface WishService {
    Wish findByCode(String code) throws NotFoundException;
    Optional<Wish> findByName(String name);
    Wish save(Wish wish);
    List<Wish> save(List<Wish> wishes);
    List<Wish> getWishesById(String id);
    Page<Wish> getAllWishLists(Pageable pageable);
}
