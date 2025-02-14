package dev.alonso.Foro.API.Spring.domain.users;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record DataCreateUser(
        @NotBlank String username,
        @Valid Credential credential
) {
}