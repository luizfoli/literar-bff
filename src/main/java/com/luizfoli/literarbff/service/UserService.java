package com.luizfoli.literarbff.service;

import com.luizfoli.literarbff.dto.ResponseDTO;
import com.luizfoli.literarbff.dto.UserDTO;
import com.luizfoli.literarbff.model.User;
import com.luizfoli.literarbff.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository repository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.repository = repository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public ResponseDTO auth(UserDTO dto) {
        User user = this.repository.findUserByEmail(dto.getEmail()).get();
        ResponseDTO response = new ResponseDTO();

        if(user == null) {
            response.setMessage("User with email {" + dto.getEmail() + "} not found");
            response.setSuccess(false);
            return response;
        }

        if(bCryptPasswordEncoder.matches(dto.getPassword(), user.getPassword())) {
            response.setSuccess(true);
            response.setMessage("Login was success");
            return response;
        }

        response.setSuccess(false);
        response.setMessage("Email and/or password wrongs");
        return response;
    }

    public Boolean save(UserDTO dto) {

        if(this.repository.findUserByEmail(dto.getEmail()).isPresent()) {
            return false;
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        user.setName(dto.getName());
        user.setLastName(dto.getLastName());

        this.repository.save(user);
        return true;
    }
}