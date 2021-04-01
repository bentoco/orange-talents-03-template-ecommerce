package br.com.zupacademy.ecommerce.user;

public class UserCreatedResponse {
    private final String message;

    public UserCreatedResponse () {
        this.message = "cadastrado realizado com sucesso.";
    }

    public String getMessage () {
        return message;
    }
}
