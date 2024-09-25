package com.tobipeter.giftdrop.db.repositories;

import com.tobipeter.giftdrop.db.models.Window;
import jakarta.transaction.Transactional;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface WindowRepository extends JpaRepository<Window, Long> {
    Optional<Window> findByCode(String code);

    @Query("SELECT w FROM Window w WHERE w.active = true")
    Optional<Window> getCurrentWindow();

    @Query("SELECT w FROM Window w WHERE w.startDate = :start AND w.endDate = :end")
    Optional<Window> existsByPeriod(
            @Param("start")LocalDate start,
            @Param("end")LocalDate end
            );

    @Transactional
    @Modifying
    @Query("UPDATE Window w SET w.active = false WHERE w.active = true")
    void deactivate();

    @Transactional
    @Modifying
    @Query("UPDATE Window w SET w.netWishLists = :value WHERE w.code = :code")
    int updateNetWishLists(@Param("code") String code, @Param("value") Long value);

    @Transactional
    @Modifying
    @Query("UPDATE Window w SET w.activeGifters = :value WHERE w.code = :code")
    int updateActiveGifters(@Param("code") String code, @Param("value") Long value);

    @Transactional
    @Modifying
    @Query("UPDATE Window w SET w.completedGifts = :value WHERE w.code = :code")
    int updateCompletedGifts(@Param("code") String code, @Param("value") Long value);

    @Transactional
    @Modifying
    @Query("UPDATE Window w SET w.percentGiftingCompleted = :value WHERE w.code = :code")
    int updatePercentGiftingCompleted(@Param("code") String code, @Param("value") Integer value);

    @Transactional
    @Modifying
    @Query("UPDATE Window w SET w.totalGifters = :value WHERE w.code = :code")
    int updateTotalGifters(@Param("code") String code, @Param("value") Long value);

    @Transactional
    @Modifying
    @Query("UPDATE Window w SET w.totalWishers = :value WHERE w.code = :code")
    int updateTotalWishers(@Param("code") String code, @Param("value") Long value);

    @Transactional
    @Modifying
    @Query("UPDATE Window w SET w.isWishing = :isWishing, w.isGifting = :isGifting WHERE w.code =:code")
    int changeStatus(
            @Param("code") String code,
            @Param("isWishing") boolean isWishing,
            @Param("isGifting") boolean isGifting
    );
}
