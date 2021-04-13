package br.com.zupacademy.ecommerce.product.purchase.payment;

import br.com.zupacademy.ecommerce.product.purchase.Purchase;
import br.com.zupacademy.ecommerce.product.purchase.PurchaseActions;
import br.com.zupacademy.ecommerce.product.purchase.PurchaseProcessed;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.BindException;
import java.util.Set;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping ( "/api/purchases/confirm-payment" )
class PaymentHandlerController {

    @PersistenceContext
    private EntityManager manager;

    private final Set<PurchaseActions> purchaseActions;

    public PaymentHandlerController ( Set<PurchaseActions> purchaseActions ) {
        this.purchaseActions = purchaseActions;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> confirmPayment (
            @RequestBody @Valid PaymentReturn paymentReturn ,
            UriComponentsBuilder builder ) throws BindException {

        Purchase purchase = manager.find(Purchase.class , paymentReturn.getPurchaseId());
        manager.merge(purchase);

        PurchaseProcessed purchaseProcessed = purchase.process(paymentReturn);
        purchaseActions.forEach(action -> action.execute(purchaseProcessed, builder));

        return ok().build();
    }
}
