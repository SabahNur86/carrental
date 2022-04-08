package com.carrentalproject.security.service;

import com.carrentalproject.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor

public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID= 1L;

    //buradaki id userName email ve password user tablomuzdaki alanlar
    private Long id;

    private String email;

    @JsonIgnore //postmande yapıcagımız testlerde password girmemiz gerekmesin diye bu anotasyonu kullandık
    private String password;

    private Collection<? extends GrantedAuthority> authorities;
    //bu variable icinde hangi role sahip oldugunu burada saklicaz.

    public static UserDetailsImpl build(User user){ //liste seklinde user ın rollerini alıyoruz ve
        List<GrantedAuthority> authorities= user.getRoles().stream()
                .map(role-> new SimpleGrantedAuthority(role.getName().name()))//her bir role grant authority tanımlıyoruz ve name i alıyoruz
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                authorities);//buradaki bilgileri donduruyoruz
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o==null || getClass()!= o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id,user.id);
    }

}
