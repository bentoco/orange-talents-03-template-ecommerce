package br.com.zupacademy.ecommerce.purchase.payment;

import br.com.zupacademy.ecommerce.purchase.Purchase;
import org.springframework.web.util.UriComponentsBuilder;

public enum PaymentGateway {
    PAGSEGURO {
        public String redirectUrlBuilder ( Purchase purchase , UriComponentsBuilder builder ) {
            String
                    urlPagseguro =
                    builder.path("api/purchase/redirect-pagseguro/{id}").buildAndExpand(purchase.getId()).toString();
            return "pagseguro.com?returnId=" + purchase.getId() + "&redirectUrl=" + urlPagseguro;
        }
    },
    PAYPAL {
        public String redirectUrlBuilder ( Purchase purchase , UriComponentsBuilder builder ) {
            String
                    urlPaypal =
                    builder.path("api/purchase/redirect-paypal/{id}").buildAndExpand(purchase.getId()).toString();
            return "paypal.com?buyerId=" + purchase.getId() + "&redirectUrl=" + urlPaypal;
        }

    };

    public abstract String redirectUrlBuilder ( Purchase purchase , UriComponentsBuilder builder );
}
