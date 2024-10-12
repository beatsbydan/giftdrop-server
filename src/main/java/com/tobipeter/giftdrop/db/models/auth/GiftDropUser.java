package com.tobipeter.giftdrop.db.models.auth;

import com.tobipeter.giftdrop.db.models.BaseModel;
import com.tobipeter.giftdrop.db.models.ExpenseRecord;
import com.tobipeter.giftdrop.db.models.Invoice;
import com.tobipeter.giftdrop.db.models.Wish;
import com.tobipeter.giftdrop.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "users")
public class GiftDropUser extends BaseModel implements UserDetails {
    private String code;

    private String wishingId;

    private String giftingId;

    private String firstName;

    private String username;

    private String password;

    private String email;

    private String address;

    private String bio;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "giftDropUser")
    private List<Wish> wishes;

    @OneToMany(mappedBy = "giftDropUser")
    private List<Invoice> invoices;

    @OneToMany(mappedBy = "giftDropUser")
    private List<ExpenseRecord> expenseRecords;

    @OneToOne
    @JoinColumn(name = "user_otp", referencedColumnName = "id")
    private Otp otp;

    private Long points;

    private boolean hasWish;

    public String generateCode(){
        return "0x" + UUID.randomUUID().toString().substring(0, 5);
    }


    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.name()));

        return authorities;
    }

    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
