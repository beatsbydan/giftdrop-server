package com.tobipeter.giftdrop.db.services.wish;

import com.tobipeter.giftdrop.db.models.Wish;
import com.tobipeter.giftdrop.db.repositories.WishRepository;
import com.tobipeter.giftdrop.enums.Status;
import com.tobipeter.giftdrop.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class WishServiceImpl implements WishService{
    private final WishRepository repository;

    @Override
    public Wish findByCode(String code) throws NotFoundException {
        return repository.findByCode(code)
                .orElseThrow(()-> new NotFoundException("Wish not found"));
    }

    @Override
    public Optional<Wish> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public Wish save(Wish wish) {
        return repository.save(wish);
    }

    @Override
    public List<Wish> save(List<Wish> wishes) {
        return repository.saveAll(wishes);
    }

    @Override
    public List<Wish> getWishesById(String id) {
        return repository.findByWishingId(id);
    }

    @Override
    public Page<Wish> getAllWishLists(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
