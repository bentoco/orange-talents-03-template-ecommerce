package br.com.zupacademy.ecommerce.purchase.events;

import br.com.zupacademy.ecommerce.purchase.Purchase;
import io.jsonwebtoken.lang.Assert;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class PurchaseInvoice implements PurchaseProcessedEvents {
    @Override public void process ( Purchase purchase ) {
        Assert.isTrue(purchase.successfullyProcessed(), "denied operation, purchase not completed successfully.");

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> request = Map.of("purchaseId", purchase.getId(),
                "buyerId", purchase.getBuyer().getId());

        restTemplate.postForEntity("http://localhost:8080/invoices",
                request, String.class);
    }
}
