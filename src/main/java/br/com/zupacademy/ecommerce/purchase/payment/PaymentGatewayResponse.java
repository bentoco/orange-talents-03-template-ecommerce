package br.com.zupacademy.ecommerce.purchase.payment;

import br.com.zupacademy.ecommerce.purchase.Purchase;

public interface PaymentGatewayResponse {
    /**
     * @param purchase
     * @return 's a new transaction depending on the specific gateway
     */
    PaymentTransaction toTransaction ( Purchase purchase );
}
