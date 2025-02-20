package dev.alonso.Foro.API.Spring.domain.posts;

import dev.alonso.Foro.API.Spring.domain.replies.Reply;
import dev.alonso.Foro.API.Spring.domain.reviews.Review;
import dev.alonso.Foro.API.Spring.domain.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;
    private String type;
    private String textOptional;
    private String imageOptional;

    @Column(nullable = false)
    private Integer likes = 0;

    @Column(nullable = false)
    private Integer repliesCount = 0;

    @Column(nullable = false)
    private Integer reviewsCount = 0;

    @Column(nullable = false)
    private Integer votesCount = 0;

    @Column(nullable = false)
    private Double votesAverage = 0.0;

    @Column(nullable = false)
    private Boolean active;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dateCreated;

    @ManyToOne(fetch = FetchType.LAZY)  //Relacion Muchos -> 1 con la tablas users, Optimiza rendimiento cargando solo cuando se necesite
    @JoinColumn(name = "user_id", nullable = false)   //Clave foranea en la tabla posts
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replies;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

    public Post(User userPost, DataNewPost dataNewPost) {
        this.user = userPost;
        this.title = dataNewPost.title();
        this.type = dataNewPost.type();
        if (!dataNewPost.textOptional().isBlank()){
            this.textOptional = dataNewPost.textOptional();
        }
        if(!dataNewPost.imageOptional().isBlank()){
            this.imageOptional = dataNewPost.imageOptional();
        }
    }

    public void like() {
        int current_likes = getLikes();
        setLikes(current_likes + 1);
    }

    //@PrePersist para inicializar dateCreated
    //Se asegura de que dateCreated siempre tenga un valor antes de ser guardado en la BD.
    @PrePersist
    protected void onCreate() {
        this.dateCreated = LocalDateTime.now();
        this.active = true;
    }
}
