package com.app.blogger.service;

import com.app.blogger.payload.LoginDto;
import com.app.blogger.payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
