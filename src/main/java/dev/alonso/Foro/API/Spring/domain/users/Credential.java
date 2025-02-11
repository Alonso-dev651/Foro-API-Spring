package dev.alonso.Foro.API.Spring.domain.users;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "credentials")
public class Credential {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email  //Validado con el formato de emails
    @Column(unique = true, nullable = false) //Un correo que sea unico y que no sea vacio
    private String email;
    private String password;

    @OneToOne   //Relacion 1 -> 1 con la tabla users
    @JoinColumn(name = "user_id") //Clave foranea en la tabla credentials
    private User user;
}
