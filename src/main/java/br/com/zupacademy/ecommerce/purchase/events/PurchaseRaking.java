package br.com.zupacademy.ecommerce.purchase.events;

import br.com.zupacademy.ecommerce.purchase.Purchase;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class PurchaseRaking implements PurchaseProcessedEvents {
    @Override public void process ( Purchase purchase ) {
        Assert.isTrue(purchase.successfullyProcessed() , "denied operation, purchase not completed successfully." + purchase);

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> request = Map.of("purchaseId" , purchase.getId() ,
                "productOwnerId" , purchase.getProductOwner().getId());

        restTemplate.postForEntity("http://localhost:8080/ranking" ,
                request , String.class);
    }
}
