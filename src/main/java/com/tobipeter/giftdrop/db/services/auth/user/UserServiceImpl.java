package com.tobipeter.giftdrop.db.services.auth.user;

import com.tobipeter.giftdrop.db.models.auth.GiftDropUser;
import com.tobipeter.giftdrop.db.repositories.auth.UserRepository;
import com.tobipeter.giftdrop.dtos.request.auth.CreateUser;
import com.tobipeter.giftdrop.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public GiftDropUser save(GiftDropUser giftDropUser) {
        return userRepository.save(giftDropUser);
    }

    @Override
    public GiftDropUser getByUsername(String username) throws NotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("This user does not exist."));
    }

    @Override
    public GiftDropUser getByEmail(String email) throws NotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("This user does not exist."));
    }

    @Override
    public GiftDropUser getByCode(String code) throws NotFoundException {
        return userRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException("This user does not exist."));
    }

    @Override
    public GiftDropUser getByWishingId(String wishingId) throws NotFoundException {
        return userRepository.findByWishingId(wishingId)
                .orElseThrow(() -> new NotFoundException("This user does not exist"));
    }

    @Override
    public Page<GiftDropUser> getPaginatedUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public List<GiftDropUser> getRankedUsers(Pageable pageable) {

        return userRepository.getRankedUsers(pageable);
    }

    @Override
    public boolean checkExistence(CreateUser request) {
        return userRepository.existsByEmailOrUsername(request.getEmail(), request.getUserName());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("This user does not exist"));
    }
}
