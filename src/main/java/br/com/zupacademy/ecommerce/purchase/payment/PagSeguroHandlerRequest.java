package br.com.zupacademy.ecommerce.purchase.payment;

import br.com.zupacademy.ecommerce.purchase.Purchase;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;

public class PagSeguroHandlerRequest implements PaymentGatewayResponse {

    @NotBlank
    private String transactionId;
    @Enumerated ( EnumType.STRING )
    private PagSeguroStatusResponse status;

    public PagSeguroHandlerRequest (
            @NotBlank String transactionId ,
            PagSeguroStatusResponse status ) {
        this.transactionId = transactionId;
        this.status = status;
    }

    @Override
    public PaymentTransaction toTransaction ( Purchase purchase ) {
        return new PaymentTransaction(status.normalize() , transactionId , purchase);
    }
}

