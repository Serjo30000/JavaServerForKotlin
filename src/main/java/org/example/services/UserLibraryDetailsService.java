package org.example.services;

import lombok.AllArgsConstructor;
import org.example.repositories.UserLibraryRepo;
import org.example.security.UserLibraryDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserLibraryDetailsService implements UserDetailsService {
    private final UserLibraryRepo aRepo;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        var v = aRepo.findByLogin(s);

        if (v.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return new UserLibraryDetails(v.get());
    }
}
