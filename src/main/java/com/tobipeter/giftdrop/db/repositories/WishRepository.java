package com.tobipeter.giftdrop.db.repositories;

import com.tobipeter.giftdrop.db.models.Wish;
import com.tobipeter.giftdrop.enums.Status;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishRepository extends JpaRepository<Wish, Long> {
    Optional<Wish> findByCode(String code);
    Optional<Wish> findByName(String code);
    @Query("SELECT w FROM Wish w WHERE w.giftDropUser.wishingId = :id")
    List<Wish> findByWishingId(@Param("id") String id);
}
