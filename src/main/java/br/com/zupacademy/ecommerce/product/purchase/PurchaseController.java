package br.com.zupacademy.ecommerce.product.purchase;

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
        var purchasePayment = request.getPaymentGateway();

        if (purchaseProduct.reserveIfHasStock(purchaseQuantity)) {
            var newPurchase = new Purchase(purchaseProduct , purchaseQuantity , purchasePayment , buyer);
            manager.persist(newPurchase);
            mailer.newPurchase(newPurchase, purchaseProduct);

            if (purchasePayment.equals(PaymentGateway.PAYPAL)) {
                //paypal.com?buyerId={idGeradoDaCompra}&redirectUrl={urlRetornoAppPosPagamento}
                String
                        urlPaypal =
                        builder.path("/redirect-paypal/{id}").buildAndExpand(newPurchase.getId()).toString();
                return "paypal.com?buyerId=" + newPurchase.getId() + "&redirectUrl=" + urlPaypal;
            } else {
                //pagseguro.com?returnId={idGeradoDaCompra}&redirectUrl={urlRetornoAppPosPagamento}
                String
                        urlPagseguro =
                        builder.path("/redirect-pagseguro/{id}").buildAndExpand(newPurchase.getId()).toString();
                return "pagseguro.com?returnId=" + newPurchase.getId() + "&redirectUrl=" + urlPagseguro;
            }
        }

        BindException outOfStockException = new BindException(request , "newPurchaseRequest");
        outOfStockException.reject("purchase.product.outOfStock" , "Sem estoque para o produto");

        throw outOfStockException;
    }
}
