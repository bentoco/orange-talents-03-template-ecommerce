package br.com.zupacademy.ecommerce.user;

public class UserPasswordClear {

    private String passwordPlainText;

    public UserPasswordClear ( String passwordPlainText ) {
        this.passwordPlainText = passwordPlainText;
    }

    public String getPasswordPlainText () {
        return passwordPlainText;
    }
}
