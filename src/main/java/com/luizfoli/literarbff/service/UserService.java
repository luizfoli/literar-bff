package com.luizfoli.literarbff.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.luizfoli.literarbff.dto.ResponseDTO;
import com.luizfoli.literarbff.dto.UserDTO;
import com.luizfoli.literarbff.model.User;
import com.luizfoli.literarbff.repository.UserRepository;

@Service
public class UserService {

    private UserRepository repository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.repository = repository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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