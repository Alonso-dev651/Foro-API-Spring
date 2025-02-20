package dev.alonso.Foro.API.Spring.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    boolean existsByTitleUrl(String title);

    Post findByTitleUrl(String title);
}
