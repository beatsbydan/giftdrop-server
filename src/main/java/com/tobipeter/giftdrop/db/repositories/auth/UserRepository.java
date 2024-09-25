package com.tobipeter.giftdrop.db.repositories.auth;

import com.tobipeter.giftdrop.db.models.auth.GiftDropUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<GiftDropUser, Long> {
    Optional<GiftDropUser> findByCode(String code);
    Optional<GiftDropUser> findByUsername(String username);
    Optional<GiftDropUser> findByEmail(String username);
    Optional<GiftDropUser> findByWishingId(String wishingId);

    @Query("SELECT u from GiftDropUser u ORDER BY u.points DESC")
    List<GiftDropUser> getRankedUsers(Pageable pageable);
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM GiftDropUser u WHERE u.email = :email OR u.username = :username")
    boolean existsByEmailOrUsername(@Param("email") String email, @Param("username") String username);

}
