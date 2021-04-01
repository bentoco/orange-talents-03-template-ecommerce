package br.com.zupacademy.ecommerce.user;

import br.com.zupacademy.ecommerce.config.validators.MustBeUnique;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.validation.constraints.*;

public class UserRequest {
    @NotBlank
    @Email
    @MustBeUnique ( field = "login", klazz = User.class )
    private String login;

    @NotBlank
    @Min ( 6 )
    private String password;

    public UserRequest (
            @NotBlank @Email String login ,
            @NotBlank @Min ( 6 ) String password ) {
        this.login = login;
        this.password = password;
    }

    public User toModel () {
        Assert.isTrue(StringUtils.hasLength(login) , "login não pode ser em branco");
        Assert.isTrue(StringUtils.hasLength(password), "senha não pode estar em branco");
        Assert.isTrue(password.length() >= 6, "senha precisa de no mínimo 6 caracteres");
        return new User(login , new UserPasswordClear(password));
    }

}
