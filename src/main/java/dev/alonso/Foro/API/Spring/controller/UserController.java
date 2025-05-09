package dev.alonso.Foro.API.Spring.controller;

import dev.alonso.Foro.API.Spring.domain.posts.DataReturnPost;
import dev.alonso.Foro.API.Spring.domain.users.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/create")
    @Transactional
    public ResponseEntity<DataReturnUser> createUser(@RequestBody @Valid DataCreateUser dataCreateUser){
        String hashedPassword = passwordEncoder.encode(dataCreateUser.credential().getPassword());
        User newUser = userRepository.save(new User(dataCreateUser, hashedPassword));
        DataReturnUser dataReturnUser = returnDataUser(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(dataReturnUser);
    }

    @GetMapping("/{username}")
    @Transactional
    public ResponseEntity<DataReturnUser> thisUser(@PathVariable String username){
        if (userRepository.existsByUsername(username)){
            User existsUser = userRepository.getReferenceByUsername(username);
            DataReturnUser dataReturnUser = returnDataUser(existsUser);
            return ResponseEntity.status(HttpStatus.OK).body(dataReturnUser);
        }else {
            return ResponseEntity.ok().build();
        }
    }

    @PutMapping("/update/{id}")
    @Transactional
    public ResponseEntity<DataReturnUser> updateUser(@PathVariable Long id, @RequestBody DataUpdateUser dataUpdateUser){
        if (userRepository.existsById(id)){
            User existsUser = userRepository.getReferenceById(id);
            existsUser.updateUser(existsUser,dataUpdateUser);
            DataReturnUser dataReturnUser = returnDataUser(existsUser);
            return ResponseEntity.status(HttpStatus.OK).body(dataReturnUser);
        } else {
            return ResponseEntity.ok().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity deleteUser(@PathVariable Long id){
        if (userRepository.existsById(id)){
            userRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }else {
            return ResponseEntity.ok().build();
        }
    }

    public DataReturnUser returnDataUser(User user){
        List<DataReturnPost> posts = user.getPosts().stream().map(
                post -> new DataReturnPost(
                        post.getUser().getUsername(), post.getTitle(), post.getType(), post.getTextOptional(), post.getImageOptional(),
                        post.getDateCreated(), post.getLikes(), post.getRepliesCount(), post.getReviewsCount(), post.getVotesCount(),
                        post.getVotesAverage(), post.getReplies(), post.getReviews()
                )
        ).toList();

        return new DataReturnUser(
                user.getId(),user.getUsername(),user.getNickname(),user.getTag(),
                user.getImageUrl(),posts,user.getReplies(),user.getReviews(),
                user.getDateCreated()
        );
    }
}
