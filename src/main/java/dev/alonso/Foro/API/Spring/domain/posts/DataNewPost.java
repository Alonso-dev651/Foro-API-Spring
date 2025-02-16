package dev.alonso.Foro.API.Spring.domain.posts;

public record DataNewPost(
        String title,
        String type,
        String textOptional,
        String imageOptional
) {
}
