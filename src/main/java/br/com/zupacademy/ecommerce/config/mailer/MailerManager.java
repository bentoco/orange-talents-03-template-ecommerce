package br.com.zupacademy.ecommerce.config.mailer;

import br.com.zupacademy.ecommerce.product.Product;
import br.com.zupacademy.ecommerce.product.purchase.Purchase;
import br.com.zupacademy.ecommerce.product.question.ProductQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Service
public class MailerManager {

    @Autowired
    private Mailer mailer;

    public void newQuestion ( @NotNull @Valid ProductQuestion question ) {
        var to = question.getUser().getLogin();
        mailer.send("html" ,
                "question" ,
                "new question" ,
                "no-reply@mercadolivre.com.br" ,
                to
        );
    }

    public void newPurchase ( @NotNull @Valid Purchase purchase , Product product ) {
        var to = product.getProductOwner().getLogin();
        mailer.send("Product " + product.getProductName() +  " has a new buyer" ,
                "new purchase ID:" + purchase.getId()  ,
                "new purchase" ,
                "no-reply@mercadolivre.com.br" ,
                to
        );
    }
}
