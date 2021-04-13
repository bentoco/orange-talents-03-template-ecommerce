package br.com.zupacademy.ecommerce.config.external;

import br.com.zupacademy.ecommerce.config.validators.MustExistId;
import br.com.zupacademy.ecommerce.purchase.Purchase;
import br.com.zupacademy.ecommerce.user.User;

import javax.validation.constraints.NotNull;

public class PurchaseInvoiceRequest {
    @NotNull
    @MustExistId ( target = "id", klazz = Purchase.class )
    private Long purchaseId;
    @NotNull
    @MustExistId ( target = "id", klazz = User.class )
    private Long buyerId;

    public PurchaseInvoiceRequest ( @NotNull Long purchaseId , @NotNull Long buyerId ) {
        this.purchaseId = purchaseId;
        this.buyerId = buyerId;
    }

    public Long getPurchaseId () {
        return purchaseId;
    }

    public Long getBuyer () {
        return buyerId;
    }
}
