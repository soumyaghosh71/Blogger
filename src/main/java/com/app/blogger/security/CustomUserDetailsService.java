package com.app.blogger.security;

import com.app.blogger.model.User;
import com.app.blogger.service.UserProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    @Autowired
    private UserProxyService userProxyService;

    @Override
    public UserDetails loadUserByUsername(String emailOrUsername) throws UsernameNotFoundException {
        ResponseEntity<User> userRes = userProxyService.findUser(emailOrUsername, "emailOrUserName");
        if (userRes.getStatusCode().isError() || userRes.getBody() == null) {
            throw new UsernameNotFoundException("User not found with username or email: " + emailOrUsername);
        }
        var user = userRes.getBody();
        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
