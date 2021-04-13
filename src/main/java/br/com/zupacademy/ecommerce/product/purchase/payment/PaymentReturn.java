package br.com.zupacademy.ecommerce.product.purchase.payment;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static br.com.zupacademy.ecommerce.product.purchase.payment.PaymentStatus.ERROR;
import static br.com.zupacademy.ecommerce.product.purchase.payment.PaymentStatus.SUCCESS;

public class PaymentReturn {

    @NotNull
    @Min ( 1 )
    private Long purchaseId;

    @NotBlank
    private String paymentId;

    @NotBlank
    private String status;

    public Long getPurchaseId () {
        return purchaseId;
    }

    public String getPaymentId () {
        return paymentId;
    }

    public String getStatus () {
        return status;
    }

    public PaymentStatus payPalStatus () {
        if (status.equals("1")) {
            return SUCCESS;
        }
        return ERROR;
    }

    public PaymentStatus pagSeguroStatus () {
        if (status.equals("SUCESSO")) {
            return SUCCESS;
        }
        return ERROR;
    }
}
