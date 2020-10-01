package com.luizfoli.literarbff.service;

import java.util.Date;

import com.luizfoli.literarbff.dto.response.AuthUserResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.luizfoli.literarbff.config.jwt.JwtUtil;
import com.luizfoli.literarbff.controller.UserController;
import com.luizfoli.literarbff.dto.AuthUserDTO;
import com.luizfoli.literarbff.dto.response.ResponseDTO;
import com.luizfoli.literarbff.model.User;
import com.luizfoli.literarbff.repository.UserRepository;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserRepository repository;
    private JwtUtil jwtUtil;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AuthenticationManager authenticationManager;
    private AuthDetailsService authDetailsService;

    public AuthService(UserRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder, JwtUtil jwtUtil,
                       AuthenticationManager authenticationManager, AuthDetailsService authDetailsService) {
        this.repository = repository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.authDetailsService = authDetailsService;
    }

    /**
     * Method reponsible for auth the user
     * @param dto
     * @return AuthUserResponseDTO object
     */

   public AuthUserResponseDTO auth(AuthUserDTO dto) {
       this.logger.info("{time_stamp: "+ new Date().getTime() +", path_req: '/auth', method: 'POST', " +
               "trace: 'controller.service', dto: " + dto.toString() + "}");

        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
        } catch(BadCredentialsException e) {
            this.logger.error(e.getMessage());
        }

        User user = this.repository.findUserByEmail(dto.getEmail()).get();
        AuthUserResponseDTO response = new AuthUserResponseDTO();

        if(user == null) {
            String message = "User with email {" + dto.getEmail() + "} not found";
            response.setMessage(message);
            response.setSuccess(false);

            this.logger.info("{time_stamp: "+ new Date().getTime() +", path_req: '/auth', method: 'POST', " +
                    "trace: 'controller.service', dto: " + dto.toString() + ", msg: '"+ message + "'}");
            return response;
        }

        if(!bCryptPasswordEncoder.matches(dto.getPassword(), user.getPassword())) {
            String message = "Email and/or password wrongs";
            response.setSuccess(false);
            response.setMessage(message);

            this.logger.info("{time_stamp: "+ new Date().getTime() +", path_req: '/auth', method: 'POST', " +
                    "trace: 'controller.service', dto: " + dto.toString() + ", msg: '"+ message + "'}");
            return response;
        }

        UserDetails userDetails = this.authDetailsService.loadUserByUsername(dto.getEmail());
        String token = jwtUtil.generateToken(userDetails);

        String message = "Login was successfully";
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setName(user.getName());
        response.setLastName(user.getLastName());
        response.setSuccess(true);
        response.setMessage(message);
        response.setToken(token);

       this.logger.info("{time_stamp: "+ new Date().getTime() +", path_req: '/auth', method: 'POST', " +
               "trace: 'controller.service', dto: " + dto.toString() + ", msg: '"+ message + "', token: '" + token + "'}");
        return response;
    };
}
