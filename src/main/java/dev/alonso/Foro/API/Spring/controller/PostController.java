package dev.alonso.Foro.API.Spring.controller;

import dev.alonso.Foro.API.Spring.domain.posts.DataNewPost;
import dev.alonso.Foro.API.Spring.domain.posts.DataReturnPost;
import dev.alonso.Foro.API.Spring.domain.posts.Post;
import dev.alonso.Foro.API.Spring.domain.posts.PostRepository;
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
            System.out.println("Si existe");
            User existsUser = userRepository.findByUsername(username);
            System.out.println(existsUser);
            Post newPost = postRepository.save(new Post(existsUser, dataNewPost));
            System.out.println("El post se guardo");
            DataReturnPost dataReturnPost = dataReturnPost(newPost);

            return ResponseEntity.status(HttpStatus.CREATED).body(dataReturnPost);
        } else {
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
