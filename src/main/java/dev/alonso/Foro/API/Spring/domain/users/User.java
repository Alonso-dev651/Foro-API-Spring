package dev.alonso.Foro.API.Spring.domain.users;

import dev.alonso.Foro.API.Spring.domain.posts.Post;
import dev.alonso.Foro.API.Spring.domain.replies.Reply;
import dev.alonso.Foro.API.Spring.domain.reviews.Review;
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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL) //Relacion 1-1 com la tabla credentials
    private Credential credential;

    @Column(nullable = false, unique = true)    // Evita usernames duplicados
    private String username;
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Tag tag;    //Enum con valores inmodificables ADMIN, MODDER, STAFF, RESPECT_USER

    private String imageUrl;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dateCreated;

    /* Relacion 1 -> muchos con la tabla posts, mapeado con el atributo user, permite CRUD elementos relacionados
       orphanRemoval elimina a los hijos si se eliminan de la lista */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replies;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

    public User(DataCreateUser dataCreateUser, String hashedPassword) {
        this.username = dataCreateUser.username();
        this.credential = new Credential();
        this.credential.setEmail(dataCreateUser.credential().getEmail());
        this.credential.setPassword(hashedPassword);
        this.credential.setUser(this); // Relación bidireccional
    }

    //@PrePersist para inicializar dateCreated
    //Se asegura de que dateCreated siempre tenga un valor antes de ser guardado en la BD.
    @PrePersist
    protected void onCreate() {
        this.dateCreated = LocalDateTime.now();
    }
}
