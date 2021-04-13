package br.com.zupacademy.ecommerce.purchase;

import br.com.zupacademy.ecommerce.config.mailer.MailerManager;
import br.com.zupacademy.ecommerce.product.Product;
import br.com.zupacademy.ecommerce.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class PurchaseController {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private MailerManager mailer;

    @PostMapping ( value = "/api/purchase" )
    @Transactional
    public String createPurchase (
            @Valid @RequestBody PurchaseRequest request ,
            @AuthenticationPrincipal User buyer ,
            UriComponentsBuilder builder
    ) throws BindException {
        var purchaseProduct = manager.find(Product.class , request.getProductId());
        var purchaseQuantity = request.getQuantity();

        if (purchaseProduct.reserveIfHasStock(purchaseQuantity)) {
            var paymentMethod = request.getPaymentGateway();
            var newPurchase = new Purchase(purchaseProduct , purchaseQuantity , paymentMethod , buyer);

            manager.persist(newPurchase);
            mailer.newPurchase(newPurchase , purchaseProduct);

            return newPurchase.urlRedirect(builder);
        }

        BindException outOfStockException = new BindException(request , "newPurchaseRequest");
        outOfStockException.reject("purchase.product.outOfStock" , "Sem estoque para o produto");

        throw outOfStockException;
    }
}
