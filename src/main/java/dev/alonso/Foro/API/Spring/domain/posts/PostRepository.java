package dev.alonso.Foro.API.Spring.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    boolean existsByTitle(String title);

    Post findByTitle(String title);
}
