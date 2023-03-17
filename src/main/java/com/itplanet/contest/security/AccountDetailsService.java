package com.itplanet.contest.security;

import com.itplanet.contest.repository.AccountRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountDetailsService implements UserDetailsService {

    private final AccountRepo accountRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepo
                .findByEmail(username)
                .map(AccountDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
