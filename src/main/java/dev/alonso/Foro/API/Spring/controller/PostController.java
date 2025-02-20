package dev.alonso.Foro.API.Spring.controller;

import dev.alonso.Foro.API.Spring.domain.posts.*;
import dev.alonso.Foro.API.Spring.domain.users.User;
import dev.alonso.Foro.API.Spring.domain.users.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @PostMapping("/{username}/new")
    @Transactional
    public ResponseEntity<DataReturnPost> newPost(@PathVariable String username, @RequestBody DataNewPost dataNewPost) {
        System.out.println(username);
        if (userRepository.existsByUsername(username)) {
            User existsUser = userRepository.findByUsername(username);
            Post newPost = postRepository.save(new Post(existsUser, dataNewPost));
            DataReturnPost dataReturnPost = dataReturnPost(newPost);

            return ResponseEntity.status(HttpStatus.CREATED).body(dataReturnPost);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{username}/{title}/like")
    @Transactional
    public ResponseEntity<DataLikesReturn> likePost(@PathVariable String username, @PathVariable String title){
        if (userRepository.existsByUsername(username) && postRepository.existsByTitleUrl(title)){
            Post postExists = postRepository.findByTitleUrl(title);
            postExists.like();
            DataLikesReturn dataLikesReturn = new DataLikesReturn(postExists.getLikes());

            return ResponseEntity.status(HttpStatus.OK).body(dataLikesReturn);
        }else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{username}/{title}")
    @Transactional
    public ResponseEntity<DataReturnPost> getPost(@PathVariable String username, @PathVariable String title){
        if (userRepository.existsByUsername(username) && postRepository.existsByTitleUrl(title)){
            Post postExists = postRepository.findByTitleUrl(title);
            DataReturnPost dataReturnPost = dataReturnPost(postExists);
            return ResponseEntity.status(HttpStatus.OK).body(dataReturnPost);
        }else {
            return ResponseEntity.badRequest().build();
        }
    }

    public DataReturnPost dataReturnPost(Post post) {
        return new DataReturnPost(
                post.getUser().getUsername(), post.getTitle(), post.getType(), post.getTextOptional(), post.getImageOptional(),
                post.getDateCreated(), post.getLikes(), post.getRepliesCount(), post.getReviewsCount(), post.getVotesCount(),
                post.getVotesAverage(), post.getReplies(), post.getReviews()
        );
    }

}
