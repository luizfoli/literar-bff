package com.luizfoli.literarbff.service;

import com.luizfoli.literarbff.model.User;
import com.luizfoli.literarbff.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class AuthDetailsService implements UserDetailsService {

    private UserRepository repository;

    public AuthDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = this.repository.findUserByEmail(username);

        return user.isPresent()
                ? new org.springframework.security.core.userdetails.User
                    (user.get().getEmail(), user.get().getPassword(), new ArrayList<>())
                : null;
    }
}
