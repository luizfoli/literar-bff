package com.luizfoli.literarbff.controller;

import com.luizfoli.literarbff.dto.ResponseDTO;
import com.luizfoli.literarbff.dto.UserDTO;
import com.luizfoli.literarbff.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public Boolean postUser(@RequestBody UserDTO dto) {
        this.logger.info("{time_stamp: "+ new Date().getTime() +", path_req: '/user', method: 'POST'}");
        return this.service.save(dto);
    }

    @PostMapping("/auth")
    public ResponseDTO post(@RequestBody UserDTO dto) {
        this.logger.info("{time_stamp: "+ new Date().getTime() +", path_req: '/user', method: 'POST'}");
        return this.service.auth(dto);
    }
}
