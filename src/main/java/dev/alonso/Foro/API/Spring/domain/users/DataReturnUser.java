package dev.alonso.Foro.API.Spring.domain.users;

import java.time.LocalDateTime;

public record DataReturnUser(
        Long id,
        String username,
        String nickname,
        LocalDateTime dateCreate
) {
}
