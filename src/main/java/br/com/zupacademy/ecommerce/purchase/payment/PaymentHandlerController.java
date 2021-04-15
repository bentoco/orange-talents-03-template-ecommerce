package br.com.zupacademy.ecommerce.purchase.payment;

import br.com.zupacademy.ecommerce.purchase.Purchase;
import br.com.zupacademy.ecommerce.purchase.PurchaseActions;
import br.com.zupacademy.ecommerce.purchase.PurchaseProcessed;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.BindException;
import java.util.Set;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping ( "/api/purchases/payment" )
public class PaymentHandlerController {
    @PersistenceContext
    private EntityManager manager;

    private final Set<PurchaseActions> purchaseActions;

    public PaymentHandlerController ( Set<PurchaseActions> purchaseActions ) {
        this.purchaseActions = purchaseActions;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> payments (
            @RequestBody @Valid PaymentReturn paymentReturn ,
            UriComponentsBuilder builder ) throws BindException {

        Purchase purchase = manager.find(Purchase.class , paymentReturn.getPurchaseId());
        PurchaseProcessed purchaseProcessed = purchase.process(paymentReturn , purchase);
        manager.merge(purchase);

        purchaseActions.forEach(action -> action.execute(purchaseProcessed , builder));
        return ok().build();
    }
}
