package com.tobipeter.giftdrop.db.services.auth.user;

import com.tobipeter.giftdrop.db.models.auth.GiftDropUser;
import com.tobipeter.giftdrop.dtos.request.auth.CreateUserDto;
import com.tobipeter.giftdrop.exceptions.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    GiftDropUser save (GiftDropUser user);
    GiftDropUser getByUsername(String username) throws NotFoundException;
    GiftDropUser getByEmail(String email) throws NotFoundException;
    GiftDropUser getByCode(String code) throws NotFoundException;
    GiftDropUser getByWishingId(String wishingId) throws NotFoundException;
    Page<GiftDropUser> getPaginatedUsers(Pageable pageable);
    List<GiftDropUser> getRankedUsers(Pageable pageable);
    boolean checkExistence(CreateUserDto dto);
}
