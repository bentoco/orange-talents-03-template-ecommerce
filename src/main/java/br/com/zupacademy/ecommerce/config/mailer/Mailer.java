package br.com.zupacademy.ecommerce.config.mailer;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public interface Mailer {

    void send (
            @NotBlank String body ,
            @NotBlank String subject ,
            @NotBlank @Email String from ,
            @NotBlank @Email String to );
}
