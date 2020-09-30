package com.luizfoli.literarbff.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.luizfoli.literarbff.config.jwt.JwtUtil;
import com.luizfoli.literarbff.controller.UserController;
import com.luizfoli.literarbff.dto.AuthUserDTO;
import com.luizfoli.literarbff.dto.ResponseDTO;
import com.luizfoli.literarbff.model.User;
import com.luizfoli.literarbff.repository.UserRepository;

@Service
public class AuthService extends implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserRepository repository;
    private JwtUtil jwtUtil;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AuthenticationManager authenticationManager;

    public AuthService(UserRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder, JwtUtil jwtUtil,
                       AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Method reponsible for auth the user
     * @param dto
     * @return ResponseDTO object
     */

   public ResponseDTO auth(AuthUserDTO dto) {
        this.logger.info("{time_stamp: "+ new Date().getTime() +", path_req: '/auth', method: 'POST', trace: 'service'}");

        try {

            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));

        } catch(BadCredentialsException e) {
            this.logger.error(e.getMessage());
        }

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = this.repository.findUserByEmail(username);

        if(!user.isPresent()) {
            return null;
        }

        return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(), new ArrayList<>());
    }
}
