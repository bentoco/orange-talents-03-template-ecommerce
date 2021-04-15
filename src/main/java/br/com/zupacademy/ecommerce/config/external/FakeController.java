package br.com.zupacademy.ecommerce.config.external;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * A fake controller to simulate externals services
 */
@RestController
public class FakeController {

    @PostMapping ( "/invoices" )
    public void createInvoice ( @Valid @RequestBody PurchaseInvoiceRequest request ) throws InterruptedException {
        System.out.println("creating invoce: " +"buyerId: "+ request.getBuyer() + " - purchaseId: " + request.getPurchaseId());
        Thread.sleep(150);
    }

    @PostMapping ( "/ranking" )
    public void createRaking ( @Valid @RequestBody PurchaseRakingRequest request ) throws InterruptedException {
        System.out.println("creating ranking: " + "sellerId: " + request.getProductOwnerId() + " -  purchaseId: " + request.getPurchaseId());
        Thread.sleep(150);
    }
}

