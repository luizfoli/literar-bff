package com.luizfoli.literarbff.repository;

import com.luizfoli.literarbff.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    public User findUserByEmail(String email);
}