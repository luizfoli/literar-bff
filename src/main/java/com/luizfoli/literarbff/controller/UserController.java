package com.luizfoli.literarbff.controller;

import com.luizfoli.literarbff.dto.ResponseDTO;
import com.luizfoli.literarbff.dto.UserDTO;
import com.luizfoli.literarbff.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseDTO post(@RequestBody UserDTO dto) {
        return this.service.auth(dto);
    }

    @PostMapping("/auth")
    public Boolean postUser(@RequestBody UserDTO dto) {
        return this.service.save(dto);
    }
}
