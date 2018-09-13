package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/response")
public class ProductController {

    @Autowired
    private Environment env;

    @Autowired
    private UserRepository repository;

    @GetMapping(path = "/ok")
    public ResponseEntity getOk() {
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/ko")
    public ResponseEntity getError() {
        return ResponseEntity.badRequest().body(new NullPointerException());
    }

    @GetMapping(path = "/not/found")
    public ResponseEntity getNotFound() {
        return ResponseEntity.notFound().build();
    }

    @GetMapping(path = "/nothing")
    public ResponseEntity<?> getUserNotFound() {
        return repository.findById(100L)
                .map(userModel -> ResponseEntity.ok().build())
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + 100));
    }

    @GetMapping(path = "/read_property")
    public String getProperty() {
        return env.getProperty("user.username");
    }

    @GetMapping(path = "/read_property/password")
    public String getPropertyPassword() {
        return env.getProperty("user.password");
    }
}
