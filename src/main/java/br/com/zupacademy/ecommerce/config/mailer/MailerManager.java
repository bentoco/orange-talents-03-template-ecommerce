package br.com.zupacademy.ecommerce.config.mailer;

import br.com.zupacademy.ecommerce.product.question.ProductQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Service
public class MailerManager {

    @Autowired
    private Mailer mailer;

    public void newQuestion( @NotNull @Valid ProductQuestion question ){
        mailer.send("html",
                "question",
                "new question",
                "newquestion@mercadolivre.com.br",
                question.getUser().getLogin());
    }
}
