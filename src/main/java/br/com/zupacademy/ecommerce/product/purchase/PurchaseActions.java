package br.com.zupacademy.ecommerce.product.purchase;

import org.springframework.web.util.UriComponentsBuilder;

public interface PurchaseActions {
    void execute( PurchaseProcessed payment, UriComponentsBuilder builder );
}
