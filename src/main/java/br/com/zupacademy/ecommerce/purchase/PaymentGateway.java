package br.com.zupacademy.ecommerce.purchase;

import br.com.zupacademy.ecommerce.purchase.payment.PaymentReturn;
import br.com.zupacademy.ecommerce.purchase.payment.PaymentStatus;
import org.springframework.web.util.UriComponentsBuilder;

public enum PaymentGateway {
    PAGSEGURO {
        @Override public String redirectUrl ( UriComponentsBuilder builder , Purchase purchase ) {
            String
                    urlPagseguro =
                    builder.path("api/purchase/redirect-pagseguro/{id}").buildAndExpand(purchase.getId()).toString();
            return "pagseguro.com?returnId=" + purchase.getId() + "&redirectUrl=" + urlPagseguro;
        }

        @Override public PaymentStatus status ( PaymentReturn paymentReturn ) {
            return paymentReturn.pagSeguroStatus();
        }
    },
    PAYPAL {
        @Override public String redirectUrl ( UriComponentsBuilder builder , Purchase purchase ) {
            String
                    urlPaypal =
                    builder.path("api/purchase/redirect-paypal/{id}").buildAndExpand(purchase.getId()).toString();
            return "paypal.com?buyerId=" + purchase.getId() + "&redirectUrl=" + urlPaypal;
        }

        @Override public PaymentStatus status ( PaymentReturn paymentReturn ) {
            return paymentReturn.payPalStatus();
        }
    };

    public abstract String redirectUrl ( UriComponentsBuilder builder , Purchase purchase );

    public abstract PaymentStatus status ( PaymentReturn paymentReturn );
}
