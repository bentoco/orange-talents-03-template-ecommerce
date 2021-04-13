package br.com.zupacademy.ecommerce.product.purchase.payment;

import br.com.zupacademy.ecommerce.product.purchase.Purchase;
import org.hibernate.validator.constraints.URL;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.constraints.NotNull;

public enum PaymentGateway {
    PAGSEGURO {
        public String redirectUrlBuilder ( Purchase purchase , UriComponentsBuilder builder ) {
            String
                    urlPagseguro =
                    builder.path("api/purchase/redirect-pagseguro/{id}").buildAndExpand(purchase.getId()).toString();
            return "pagseguro.com?returnId=" + purchase.getId() + "&redirectUrl=" + urlPagseguro;
        }

        @Override public PaymentStatus status ( PaymentReturn payment ) {
            return payment.pagSeguroStatus();
        }
    },
    PAYPAL {
        public String redirectUrlBuilder ( Purchase purchase , UriComponentsBuilder builder ) {
            String
                    urlPaypal =
                    builder.path("api/purchase/redirect-paypal/{id}").buildAndExpand(purchase.getId()).toString();
            return "paypal.com?buyerId=" + purchase.getId() + "&redirectUrl=" + urlPaypal;
        }

        @Override public PaymentStatus status ( PaymentReturn payment ) {
            return payment.payPalStatus();
        }

    };

    public abstract String redirectUrlBuilder ( Purchase purchase , UriComponentsBuilder builder );

    public abstract PaymentStatus status ( PaymentReturn payment );
}
