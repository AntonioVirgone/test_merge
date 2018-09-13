package com.example.demo;

import com.example.demo.security.PasswordUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping(value = "/security")
public class SecurityController {

    @GetMapping(path = "/generate")
    public ResponseEntity generatePassword(@PathParam(value = "password") String password) {
        // Generate Salt. The generated value can be stored in DB.
        String salt = PasswordUtils.getSalt(30);

        // Protect user's password. The generated value can be stored in DB.
        String mySecurePassword = PasswordUtils.generateSecurePassword(password, salt);

        // Print out protected password
        System.out.println("My secure password = " + mySecurePassword);
        System.out.println("Salt value = " + salt);

        PasswordResponse passwordResponse = new PasswordResponse();
        passwordResponse.setPassword(password);
        passwordResponse.setSecurityPassword(mySecurePassword);
        passwordResponse.setSalt(salt);

        return ResponseEntity.ok(passwordResponse);
    }

    @GetMapping(path = "/verify")
    public ResponseEntity verifyPassword(@PathParam(value = "password") String password,
                                         @PathParam(value = "securePassword") String securePassword,
                                         @PathParam(value = "salt") String salt) {
        boolean passwordMatch = PasswordUtils.verifyUserPassword(password, securePassword, salt);

        System.out.println("ciao Pippo bello");

        if(passwordMatch) {
            return ResponseEntity.ok("Provided user password " + password + " is correct.");
        } else {
            return ResponseEntity.ok("Provided password is incorrect");
        }
    }

    class PasswordResponse {
        private String password;
        private String securityPassword;
        private String salt;

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getSecurityPassword() {
            return securityPassword;
        }

        public void setSecurityPassword(String securityPassword) {
            this.securityPassword = securityPassword;
        }

        public String getSalt() {
            return salt;
        }

        public void setSalt(String salt) {
            this.salt = salt;
        }
    }
}
