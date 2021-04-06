package br.com.zupacademy.ecommerce.config.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;

    public String getEmail () {
        return email;
    }

    public void setEmail ( String email ) {
        this.email = email;
    }

    public String getPassword () {
        return password;
    }

    public void setPassword ( String password ) {
        this.password = password;
    }

    public UsernamePasswordAuthenticationToken build () {
        return new UsernamePasswordAuthenticationToken(this.email , this.password);
    }
}
