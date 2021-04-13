package br.com.zupacademy.ecommerce.purchase.payment;

import br.com.zupacademy.ecommerce.purchase.events.PurchaseNewEvents;
import br.com.zupacademy.ecommerce.purchase.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class PaymentHandlerController {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private PurchaseNewEvents purchaseNewEvents;


    @PostMapping ( "/api/purchase/redirect-paypal/{id}" )
    @Transactional
    public String paypalHandler ( @PathVariable ( "id" ) Long purchaseId , @Valid PayPalHandlerRequest request ) {
        return process(purchaseId , request);
    }

    @PostMapping ( "api/purchase/redirect-pagseguro/{id}" )
    @Transactional
    public String pagseguroHandler ( @PathVariable ( "id" ) Long purchaseId , @Valid PagSeguroHandlerRequest request ) {
        return process(purchaseId , request);
    }

    private String process ( Long purchaseId , PaymentGatewayResponse paymentGatewayResponse ) {
        var purchase = manager.find(Purchase.class , purchaseId);
        purchase.paymentAttempt(paymentGatewayResponse);
        manager.merge(purchase);
        purchaseNewEvents.process(purchase);
        return purchase.toString();
    }

}
