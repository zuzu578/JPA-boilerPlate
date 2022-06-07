package com.jpa.boilerplate.jpaboilerplate.controller.LoginController;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.jpa.boilerplate.jpaboilerplate.entity.User.UserEntity;
import com.jpa.boilerplate.jpaboilerplate.repository.User.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class LoginController {

    @Autowired
    UserRepository userRepository;

    Optional<UserEntity> uOptional;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserEntity user) {
        String id = user.getId();
        String password = user.getPassword();
        uOptional = userRepository.findByIdAndPassword(id, password);

        if (uOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("아이디 혹은 비밀번호를 확인해주세요", HttpStatus.BAD_REQUEST);
        }

    }

}
