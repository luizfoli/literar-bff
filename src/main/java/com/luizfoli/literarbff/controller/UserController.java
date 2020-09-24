package com.luizfoli.literarbff.controller;

import com.luizfoli.literarbff.dto.ResponseDTO;
import com.luizfoli.literarbff.dto.UserDTO;
import com.luizfoli.literarbff.service.UserService;

import org.apache.logging.log4j.Level;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
@RequestMapping("/user")
public class UserController {

    private Logger logger = LogManager.getLogger();
    private Level level = logger.getLevel();
    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public Boolean postUser(@RequestBody UserDTO dto) {
        this.logger.log(level,"{path_req: '/user', method: 'POST'}");
        return this.service.save(dto);
    }

    @PostMapping("/auth")
    public ResponseDTO post(@RequestBody UserDTO dto) {
        this.logger.log(level, "{path_req: '/user/auth', method: 'POST'}");
        return this.service.auth(dto);
    }
}
