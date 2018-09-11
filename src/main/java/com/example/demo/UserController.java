package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserRepository repository;

    @GetMapping(path = "/")
    public void save() {
        UserModel user = new UserModel();
        user.setUsername("Pippo");
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        repository.save(user);
    }

    @GetMapping(path = "/method-name")
    public String getMethodName() {
//        String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
        return Utils.getMethodNameFromUtils();
    }
}
