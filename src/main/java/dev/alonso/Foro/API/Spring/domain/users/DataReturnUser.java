package dev.alonso.Foro.API.Spring.domain.users;

import dev.alonso.Foro.API.Spring.domain.posts.Post;
import dev.alonso.Foro.API.Spring.domain.replies.Reply;
import dev.alonso.Foro.API.Spring.domain.reviews.Review;

import java.time.LocalDateTime;
import java.util.List;

public record DataReturnUser(
        Long id,
        String username,
        String nickname,
        Tag tag,
        String imageUrl,
        List<Post> posts,
        List<Reply> replies,
        List<Review> reviews,
        LocalDateTime dateCreate
) {
}
