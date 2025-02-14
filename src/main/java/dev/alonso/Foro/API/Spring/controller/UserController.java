package dev.alonso.Foro.API.Spring.controller;

import dev.alonso.Foro.API.Spring.domain.users.DataCreateUser;
import dev.alonso.Foro.API.Spring.domain.users.DataReturnUser;
import dev.alonso.Foro.API.Spring.domain.users.User;
import dev.alonso.Foro.API.Spring.domain.users.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/created")
    @Transactional
    public ResponseEntity<DataReturnUser> createUser(@RequestBody @Valid DataCreateUser dataCreateUser){
        String hashedPassword = passwordEncoder.encode(dataCreateUser.credential().getPassword());
        User newUser = userRepository.save(new User(dataCreateUser, hashedPassword));

        DataReturnUser dataReturnUser = new DataReturnUser(
                newUser.getId(),newUser.getUsername(),newUser.getNickname(),newUser.getDateCreated()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(dataReturnUser);
    }
}
