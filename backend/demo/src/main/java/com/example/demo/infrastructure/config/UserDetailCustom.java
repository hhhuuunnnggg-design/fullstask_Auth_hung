package com.example.demo.infrastructure.config;

import java.util.Collections;
import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demo.application.usecase.user.GetUserByEmailUseCase;

@Component("userDetailsService")
public class UserDetailCustom implements UserDetailsService {

    private final GetUserByEmailUseCase getUserByEmailUseCase;

    public UserDetailCustom(GetUserByEmailUseCase getUserByEmailUseCase) {
        this.getUserByEmailUseCase = getUserByEmailUseCase;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<com.example.demo.domain.entity.User> userOpt = this.getUserByEmailUseCase.execute(username);
        if (userOpt.isEmpty()) {
            throw new UsernameNotFoundException("Username/password không hợp lệ");
        }
        com.example.demo.domain.entity.User domainUser = userOpt.get();
        return new User(
                domainUser.getEmail(),
                domainUser.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }

}

