package com.app.blogger.service;

import com.app.blogger.model.User;
import com.app.blogger.payload.LoginDto;
import com.app.blogger.payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    User register(RegisterDto registerDto);
}
