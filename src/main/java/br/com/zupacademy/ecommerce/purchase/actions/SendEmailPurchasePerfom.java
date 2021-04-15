package br.com.zupacademy.ecommerce.purchase.actions;

import br.com.zupacademy.ecommerce.config.mailer.Mailer;
import br.com.zupacademy.ecommerce.product.Product;
import br.com.zupacademy.ecommerce.purchase.PurchaseActions;
import br.com.zupacademy.ecommerce.purchase.PurchaseProcessed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class SendEmailPurchasePerfom implements PurchaseActions {

    @Autowired
    private final Mailer mailer;

    public SendEmailPurchasePerfom ( Mailer mailer ) {
        this.mailer = mailer;
    }

    /**
     * perform the action if the purchase is made
     *
     * @param payment object that represents the purchase with successful payment
     * @param builder to create HTTP URL string types
     */
    @Override public void execute (
            PurchaseProcessed payment , UriComponentsBuilder builder ) {

        if(!payment.paymentSucessfully()){
            return;
        }
        Product product = payment.getPurchaseProduct();
        String
                body =
                "Your " + payment.getPurchaseQuantity() + " product(s): " + product.getProductName() + " is being prepared!";
        String subject = "successful purchase";
        String from = "no-reply@mercadolivre.com.br";
        String to = payment.getBuyer().getLogin();

        mailer.send(body , subject , from , to);
    }
}
