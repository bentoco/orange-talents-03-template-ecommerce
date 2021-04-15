package br.com.zupacademy.ecommerce.purchase;

import br.com.zupacademy.ecommerce.product.Product;
import br.com.zupacademy.ecommerce.user.User;
import org.springframework.web.util.UriComponentsBuilder;

public class PurchaseProcessed {

    private final Purchase purchase;

    public PurchaseProcessed ( Purchase purchase ) {
        this.purchase = purchase;
    }

    public Long getId () {
        return purchase.getId();
    }

    public Product getPurchaseProduct () {
        return purchase.getPurchaseProduct();
    }

    public int getPurchaseQuantity () {
        return purchase.getPurchaseQuantity();
    }

    public User getBuyer () {
        return purchase.getBuyer();
    }

    public User getSeller () {
        return purchase.getSeller();
    }

    public String urlRedirect ( UriComponentsBuilder builder ) {
        return purchase.urlRedirect(builder);
    }

    public boolean paymentSucessfully () {
        return purchase.paymentSucessfully();
    }
}
