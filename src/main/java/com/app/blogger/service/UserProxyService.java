package com.app.blogger.service;


import com.app.blogger.model.User;
import com.app.blogger.payload.LoginDto;
import com.app.blogger.payload.RegisterDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "blog-user-service")
public interface UserProxyService {
    @GetMapping("/findBy/{findBy}/{value}")
    public ResponseEntity<User> findUser(@PathVariable String value, @PathVariable String findBy);

    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto);

    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<User> register(@RequestBody RegisterDto loginDto);
}
