package com.coolgame.security.controllers;

import com.coolgame.security.config.JwtUtils;
import com.coolgame.security.dao.SecurityUser;
import com.coolgame.security.dto.AuthenticationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final SecurityUser securityUser;
    private final JwtUtils jwtUtils;

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(
            @RequestBody AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        final UserDetails user = securityUser.findUserByUsername(request.getUsername());
        if (user != null) {
            return ResponseEntity.ok(jwtUtils.generateToken(user));
        }
        return ResponseEntity.status(400).body("Some error has occured");
    }
}
