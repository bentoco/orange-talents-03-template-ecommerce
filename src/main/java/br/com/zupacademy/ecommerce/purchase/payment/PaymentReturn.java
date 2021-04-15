package br.com.zupacademy.ecommerce.purchase.payment;

import br.com.zupacademy.ecommerce.config.validators.MustExistId;
import br.com.zupacademy.ecommerce.purchase.Purchase;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static br.com.zupacademy.ecommerce.purchase.payment.PaymentStatus.ERROR;
import static br.com.zupacademy.ecommerce.purchase.payment.PaymentStatus.SUCCESS;

public class PaymentReturn {

    @MustExistId ( target = "id", klazz = Purchase.class )
    private @NotNull Long purchaseId;
    private @NotBlank String paymentId;
    private @NotBlank String status;

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
