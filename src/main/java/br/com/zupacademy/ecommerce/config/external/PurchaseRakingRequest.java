package br.com.zupacademy.ecommerce.config.external;

import br.com.zupacademy.ecommerce.config.validators.MustExistId;
import br.com.zupacademy.ecommerce.purchase.Purchase;
import br.com.zupacademy.ecommerce.user.User;

import javax.validation.constraints.NotNull;

public class PurchaseRakingRequest {

    @NotNull
    @MustExistId ( target = "id", klazz = Purchase.class )
    private Long purchaseId;
    @NotNull
    @MustExistId ( target = "id", klazz = User.class )
    private Long productOwnerId;

    public PurchaseRakingRequest (
            @NotNull Long purchaseId ,
            @NotNull Long productOwnerId ) {
        this.purchaseId = purchaseId;
        this.productOwnerId = productOwnerId;
    }

    public Long getPurchaseId () {
        return purchaseId;
    }

    public Long getProductOwnerId () {
        return productOwnerId;
    }
}
