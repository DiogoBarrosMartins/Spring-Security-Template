package com.coolgame.security.dao;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Repository
public class SecurityUser {


    private final List<UserDetails> APPLICATION_USERS = Arrays.asList(
            new User(
                    "",
                    "",
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))),
            new User(
                        "",
                    "",
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))

            )
    );

    public UserDetails findUserByUsername(String username){
     return   APPLICATION_USERS
                .stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("no user was found "));
    }
}


