package br.com.zupacademy.ecommerce.user;

import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.*;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;

@Entity
public class User {
    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY )
    private Long id;

    @Column ( nullable = false, unique = true )
    private String login;

    @Column ( nullable = false )
    private String password;

    @Past
    @Column ( nullable = false )
    private LocalDateTime createdAt;

    private final static String salt = BCrypt.gensalt(10);

    public User ( String login , UserPasswordClear password ) {
        this.login = login;
        this.password = BCrypt.hashpw(password.getPasswordPlainText() , salt);
        createdAt = LocalDateTime.now();
    }

    @Deprecated
    public User () {

    }

    public Long getId () {
        return id;
    }

    public String getLogin () {
        return login;
    }
}
