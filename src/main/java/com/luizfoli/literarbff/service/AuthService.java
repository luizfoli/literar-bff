package com.luizfoli.literarbff.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.luizfoli.literarbff.config.jwt.JwtUtil;
import com.luizfoli.literarbff.controller.UserController;
import com.luizfoli.literarbff.dto.AuthUserDTO;
import com.luizfoli.literarbff.dto.ResponseDTO;
import com.luizfoli.literarbff.model.User;
import com.luizfoli.literarbff.repository.UserRepository;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserRepository repository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private JwtUtil jwtUtil;


    public AuthService(UserRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder, JwtUtil jwtUtil) {
        this.repository = repository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Method reponsible for auth the user
     * @param dto
     * @return ResponseDTO object
     */

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

        String token = jwtUtil.generateToken(user.getId());

        String message = "Login was successfull";
        response.setSuccess(true);
        response.setMessage(message);
        response.setToken(token);

       this.logger.info("{time_stamp: "+ new Date().getTime() +", " +
               "path_req: '/auth', " +
               "method: 'POST', trace: 'service', " +
               "obj: {user_id: " + user.getId() + ", " +
               "token: '" + token + "', " +
               "msg: '" + message + "'}}");

        return response;
    };
}
