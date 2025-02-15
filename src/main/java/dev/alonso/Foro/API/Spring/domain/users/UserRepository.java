package dev.alonso.Foro.API.Spring.domain.users;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    User getReferenceByUsername(String username);
}
