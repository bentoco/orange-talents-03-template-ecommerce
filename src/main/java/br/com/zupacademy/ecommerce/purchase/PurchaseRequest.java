package br.com.zupacademy.ecommerce.purchase;

import br.com.zupacademy.ecommerce.config.validators.MustExistId;
import br.com.zupacademy.ecommerce.product.Product;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class PurchaseRequest {

    @NotNull
    @Positive
    private int quantity;

    @MustExistId ( target = "id", klazz = Product.class )
    private Long productId;

    @Enumerated ( EnumType.STRING )
    @NotNull
    private PaymentGateway paymentGateway;


    public PurchaseRequest (
            @NotNull @Positive int quantity ,
            Long productId ,
            @NotNull PaymentGateway paymentGateway ) {
        this.quantity = quantity;
        this.productId = productId;
        this.paymentGateway = paymentGateway;
    }

    public int getQuantity () {
        return quantity;
    }

    public Long getProductId () {
        return productId;
    }

    public PaymentGateway getPaymentGateway () {
        return paymentGateway;
    }
}
