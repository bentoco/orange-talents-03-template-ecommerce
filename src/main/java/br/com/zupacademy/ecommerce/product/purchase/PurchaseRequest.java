package br.com.zupacademy.ecommerce.product.purchase;

import br.com.zupacademy.ecommerce.config.validators.MustExistId;
import br.com.zupacademy.ecommerce.product.Product;
import br.com.zupacademy.ecommerce.product.purchase.payment.PaymentGateway;
import br.com.zupacademy.ecommerce.user.User;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class PurchaseRequest {

    @MustExistId ( target = "id", klazz = Product.class )
    private Long productId;

    @Min ( 1 )
    private int quantity;

    @NotNull
    private PaymentGateway paymentGateway;

    public Long getProductId () {
        return productId;
    }

    public int getQuantity () {
        return quantity;
    }

    public PaymentGateway getPaymentGateway () {
        return paymentGateway;
    }

    public Purchase toPurchase ( User productBuyer , Product product ) {
        return new Purchase(product , quantity , paymentGateway , productBuyer);
    }
}
