package dev.alonso.Foro.API.Spring.domain.posts;

import dev.alonso.Foro.API.Spring.domain.replies.Reply;
import dev.alonso.Foro.API.Spring.domain.reviews.Review;

import java.time.LocalDateTime;
import java.util.List;

public record DataReturnPost(
        String username,
        String title,
        String type,
        String textOptional,
        String imageOptional,
        LocalDateTime dateCreated,
        Integer likes,
        Integer repliesCount,
        Integer reviewsCount,
        Integer votesCount,
        Double votesAverage,
        List<Reply> replies,
        List<Review> reviews
) {
}
