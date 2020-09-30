package com.luizfoli.literarbff.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luizfoli.literarbff.dto.AuthUserDTO;
import com.luizfoli.literarbff.dto.ResponseDTO;
import com.luizfoli.literarbff.service.AuthService;

import java.util.Date;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseDTO post(@RequestBody AuthUserDTO dto) {
        this.logger.info("{time_stamp: "+ new Date().getTime() +", path_req: '/auth', method: 'POST', " +
                "trace: 'controller', dto: " + dto.toString() + "}");
        return this.service.auth(dto);
    }

}
