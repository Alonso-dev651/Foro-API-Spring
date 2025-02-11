package dev.alonso.Foro.API.Spring.domain.reviews;

import dev.alonso.Foro.API.Spring.domain.posts.Post;
import dev.alonso.Foro.API.Spring.domain.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private Integer vote = 0;

    @Column(nullable = false)
    private Integer likes = 0;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dateCreated;

    @ManyToOne(fetch = FetchType.LAZY)  //Relacion Muchos -> 1 con la tablas users
    @JoinColumn(name = "user_id")   //Clave foranea en la tabla reviews
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @PrePersist
    protected void onCreate() {
        this.dateCreated = LocalDateTime.now();
    }
}
