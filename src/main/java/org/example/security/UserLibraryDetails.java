package org.example.security;

import lombok.AllArgsConstructor;
import org.example.models.UserLibrary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
public class UserLibraryDetails implements UserDetails {
    private final UserLibrary aUserLibrary;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(aUserLibrary.getRole().getNameRole()));
    }

    @Override
    public String getPassword() {
        return aUserLibrary.getPassword();
    }

    @Override
    public String getUsername() {
        return aUserLibrary.getLogin();
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
}
