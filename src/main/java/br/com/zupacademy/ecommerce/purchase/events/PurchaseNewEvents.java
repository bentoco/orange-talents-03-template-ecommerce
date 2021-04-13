package br.com.zupacademy.ecommerce.purchase.events;

import br.com.zupacademy.ecommerce.purchase.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PurchaseNewEvents {

    @Autowired
    private Set<PurchaseProcessedEvents> purchaseProcessedEventsSet;

    public void process ( Purchase purchase ) {
        if (purchase.successfullyProcessed()) {
            purchaseProcessedEventsSet.forEach(p -> p.process(purchase));
        } else {
            //fail
        }
    }
}
