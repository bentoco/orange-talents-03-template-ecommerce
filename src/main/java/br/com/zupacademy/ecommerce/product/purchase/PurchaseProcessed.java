package br.com.zupacademy.ecommerce.product.purchase;

import br.com.zupacademy.ecommerce.product.Product;
import br.com.zupacademy.ecommerce.user.User;

public class PurchaseProcessed {

    private final Purchase purchase;

    public PurchaseProcessed ( Purchase purchase ) {
        this.purchase = purchase;
    }

    public boolean isPaymentSuccessful() {
        return purchase.isPaymentSuccessful();
    }

    public Long getId() {
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

    public User getProductOwner () {
        return purchase.getProductOwner();
    }


}
