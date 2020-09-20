package com.luizfoli.literarbff.service;

import com.luizfoli.literarbff.dto.ResponseDTO;
import com.luizfoli.literarbff.dto.UserDTO;
import com.luizfoli.literarbff.model.User;
import com.luizfoli.literarbff.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public ResponseDTO auth(UserDTO dto) {
        User user = this.repository.findUserByEmail(dto.getEmail());
        ResponseDTO response = new ResponseDTO();

        if(user == null) {
            response.setMessage("User with email {" + dto.getEmail() + "} not found");
            response.setSuccess(false);
            return response;
        }

        if(dto.getPassword().equals(user.getPassword())) {
            response.setSuccess(true);
            response.setMessage("Login was success");
            return response;
        }

        response.setSuccess(false);
        response.setMessage("Email and/or password wrongs");
        return response;
    }

    public Boolean save(UserDTO dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setName(dto.getName());
        user.setLastName(dto.getLastName());

        this.repository.save(user);
        return true;
    }
}