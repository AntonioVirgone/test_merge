package com.example.demo;

import com.example.demo.security.DecryptPassword;
import com.example.demo.security.EncryptPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Value("${user.username}")
    private String username;

    @Autowired
    private UserRepository repository;

    @GetMapping(path = "/sayHello")
    public void sayHello() {
        System.out.println("Hello " + username);
    }

    @GetMapping(path = "/sayHello2")
    public void sayHello2() {
        System.out.println("Hello2 " + username);
    }

    @PostMapping(path = "/save")
    public void save(@RequestBody UserModel user) throws InvalidKeySpecException, NoSuchAlgorithmException {
        byte[] salt = EncryptPassword.getSalt();
        String passwordHash = EncryptPassword.generateStorngPasswordHash(user.getPassword(), salt);
        user.setPassword(passwordHash);
        user.setSalt(salt);
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        repository.save(user);
    }

    @PostMapping(path = "/login")
    public void login(@Context HttpServletRequest request) throws InvalidKeySpecException, NoSuchAlgorithmException {
        if (request.getHeader(HttpHeaders.AUTHORIZATION).contains("Basic")) {
            String[] baseAuthorization = request.getHeader(HttpHeaders.AUTHORIZATION).split(" ", 2);
            final byte[] decodedBytes = Base64.getDecoder().decode(baseAuthorization[1]);
            final String pair = new String(decodedBytes);
            final String[] userDetails = pair.split(":", 2);

            UserModel userTemp = repository.findByUsername(userDetails[0]);

            String passwordHash = DecryptPassword.generateStorngPasswordHash(userDetails[1], userTemp.getSalt());
//            DecryptPassword.validatePassword(userDetails[1], passwordHash);

            System.out.println(passwordHash);

            UserModel userLogged = repository.findByUsernameAndPassword(userDetails[0], passwordHash);
            System.out.println(userLogged);
        }



    }

    @GetMapping(path = "/method-name")
    public String getMethodName() {
//        String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
        return Utils.getMethodNameFromUtils();
    }

    @PutMapping(path = "/{id}")
    public void update(@PathVariable Long id, @RequestBody UserModel user) {
        Optional<UserModel> userOptional = repository.findById(id);
        if (userOptional.isPresent()) {
            user.setId(id);
            user.setUpdatedAt(new Date());
            repository.save(user);
        }
    }


}
