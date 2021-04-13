package br.com.zupacademy.ecommerce.product.purchase;

import br.com.zupacademy.ecommerce.product.Product;
import br.com.zupacademy.ecommerce.user.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping ( "/api/purchase" )
class PurchaseController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping
    @Transactional
    public String buyProduct (
            @Valid @RequestBody PurchaseRequest request ,
            @AuthenticationPrincipal User buyer ,
            UriComponentsBuilder builder
    ) throws BindException {

        Product purchaseProduct = manager.find(Product.class , request.getProductId());
        Optional<Purchase> optionalPurchase = purchaseProduct.reserveIfHasStock(request , buyer);

        if (optionalPurchase.isEmpty()) {
            BindException outOfStockException = new BindException(request , "newPurchaseRequest");
            outOfStockException.reject("purchase.product.outOfStock" , "out of stock");
            throw outOfStockException;
        }

        Purchase newPurchase = optionalPurchase.get();
        manager.persist(newPurchase);

        return newPurchase.urlRedirect(builder);
    }
}
