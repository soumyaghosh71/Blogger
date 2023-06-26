package com.app.blogger.service.impl;

import com.app.blogger.exception.BlogAPIException;
import com.app.blogger.model.User;
import com.app.blogger.payload.LoginDto;
import com.app.blogger.payload.RegisterDto;
import com.app.blogger.service.AuthService;
import com.app.blogger.service.UserProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;

    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserProxyService userProxyService;

    public AuthServiceImpl(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;

        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmailOrUsername(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "User logged in successfully.";
    }

    @Override
    public User register(RegisterDto registerDto) {
        ResponseEntity<User> userProxyServiceUser = userProxyService.register(registerDto);
        if (userProxyServiceUser.getStatusCode().is2xxSuccessful()) {
            return userProxyServiceUser.getBody();
        }
        throw new BlogAPIException(HttpStatus.resolve(userProxyServiceUser.getStatusCode().value()), "");
    }
}
