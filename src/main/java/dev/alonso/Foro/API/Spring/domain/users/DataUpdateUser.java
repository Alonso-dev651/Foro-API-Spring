package dev.alonso.Foro.API.Spring.domain.users;

public record DataUpdateUser(
        String nickname,
        Tag tag,
        String imageUrl
) {
}
