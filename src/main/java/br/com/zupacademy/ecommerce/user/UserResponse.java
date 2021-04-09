package br.com.zupacademy.ecommerce.user;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class UserResponse {

    private Long id;
    private String login;
    private LocalDateTime createdAt;

    public UserResponse ( User user ) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.createdAt = user.getCreatedAt();
    }

    public Long getId () {
        return id;
    }

    public String getLogin () {
        return login;
    }

    public LocalDateTime getCreatedAt () {
        return createdAt;
    }

    public static List<UserResponse> toDto ( List<User> users ) {
        return users.stream().map(UserResponse::new).collect(Collectors.toList());
    }
}
