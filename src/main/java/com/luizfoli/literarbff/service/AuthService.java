package com.luizfoli.literarbff.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.luizfoli.literarbff.controller.UserController;
import com.luizfoli.literarbff.dto.AuthUserDTO;
import com.luizfoli.literarbff.dto.ResponseDTO;
import com.luizfoli.literarbff.model.User;
import com.luizfoli.literarbff.repository.UserRepository;

import java.util.Date;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserRepository repository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService(UserRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.repository = repository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public ResponseDTO auth(AuthUserDTO dto) {
        this.logger.info("{time_stamp: "+ new Date().getTime() +", path_req: '/auth', method: 'POST', trace: 'service'}");

        User user = this.repository.findUserByEmail(dto.getEmail()).get();
        ResponseDTO response = new ResponseDTO();

        if(user == null) {

            String message = "User with email {" + dto.getEmail() + "} not found";
            this.logger.error("{time_stamp: "+ new Date().getTime() +", path_req: '/auth', " +
                    "method: 'POST', trace: 'service', msg: "+ message + "}");

            response.setMessage(message);
            response.setSuccess(false);

            return response;
        }

        if(!bCryptPasswordEncoder.matches(dto.getPassword(), user.getPassword())) {

            String message = "Email and/or password wrongs";
            this.logger.error("{time_stamp: "+ new Date().getTime() +", path_req: '/auth', " +
                    "method: 'POST', trace: 'service', msg: "+ message + "}");

            response.setSuccess(false);
            response.setMessage(message);
            return response;
        }

        String message = "Login was successfull";
        this.logger.error("{time_stamp: "+ new Date().getTime() +", path_req: '/auth', " +
                "method: 'POST', trace: 'service', msg: "+ message + "}");

        response.setSuccess(true);
        response.setMessage("Login was success");

        return response;
    };
}
