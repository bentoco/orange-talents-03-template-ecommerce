package br.com.zupacademy.ecommerce.purchase.payment;

import br.com.zupacademy.ecommerce.purchase.Purchase;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class PayPalHandlerRequest implements PaymentGatewayResponse {

    @NotBlank
    private String transactionId;
    @Min ( 0 )
    @Max ( 1 )
    private int status;

    public PayPalHandlerRequest (
            @NotBlank String transactionId ,
            @Min ( 0 ) @Max ( 1 ) int status ) {
        this.transactionId = transactionId;
        this.status = status;
    }

    @Override
    public PaymentTransaction toTransaction ( Purchase purchase ) {
        PaymentTransactionStatus
                statusResponse = this.status == 0 ?
                PaymentTransactionStatus.error : PaymentTransactionStatus.success;
        return new PaymentTransaction(statusResponse , transactionId , purchase);
    }
}
