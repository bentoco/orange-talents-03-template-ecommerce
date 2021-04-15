package br.com.zupacademy.ecommerce.purchase.actions;

import br.com.zupacademy.ecommerce.purchase.PurchaseActions;
import br.com.zupacademy.ecommerce.purchase.PurchaseProcessed;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class PurchaseRanking implements PurchaseActions {
    @Override public void execute (
            PurchaseProcessed payment , UriComponentsBuilder builder ) {

        Assert.isTrue(payment.paymentSucessfully() , "denied operation, purchase not completed successfully.");

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> request = Map.of("purchaseId" , payment.getId() ,
                "productOwnerId" , payment.getSeller().getId());

        restTemplate.postForEntity("http://localhost:8080/ranking" ,
                request , String.class);
    }
}

