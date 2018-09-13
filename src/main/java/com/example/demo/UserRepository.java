package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long> {

    UserModel findByUsername(String username);
    UserModel findByUsernameAndPassword(String username, String password);

}
