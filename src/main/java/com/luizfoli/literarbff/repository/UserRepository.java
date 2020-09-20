package com.luizfoli.literarbff.repository;

import com.luizfoli.literarbff.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    public Optional<User> findUserByEmail(String email);
}