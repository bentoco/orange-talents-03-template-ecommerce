package br.com.zupacademy.ecommerce.product.purchase;

import br.com.zupacademy.ecommerce.product.Product;
import br.com.zupacademy.ecommerce.product.purchase.payment.Payment;
import br.com.zupacademy.ecommerce.product.purchase.payment.PaymentGateway;
import br.com.zupacademy.ecommerce.product.purchase.payment.PaymentReturn;
import br.com.zupacademy.ecommerce.product.purchase.payment.PaymentStatus;
import br.com.zupacademy.ecommerce.user.User;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Purchase {

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY )
    private Long id;

    @ManyToOne
    @NotNull
    @Valid
    private Product purchaseProduct;

    @Positive
    private int purchaseQuantity;

    @Enumerated ( EnumType.STRING )
    @NotNull
    private PaymentGateway paymentGateway;

    @ManyToOne
    @NotNull
    @Valid
    private User buyer;

    @OneToMany ( mappedBy = "purchase", cascade = CascadeType.MERGE )
    private Set<Payment> transactions = new HashSet<>();

    public Purchase (
            @NotNull @Valid Product purchaseProduct ,
            @Positive int purchaseQuantity ,
            @NotNull PaymentGateway paymentGateway ,
            @NotNull @Valid User buyer ) {
        this.purchaseProduct = purchaseProduct;
        this.purchaseQuantity = purchaseQuantity;
        this.paymentGateway = paymentGateway;
        this.buyer = buyer;
    }

    @Deprecated
    public Purchase () {
    }

    public Long getId () {
        return id;
    }

    public Product getPurchaseProduct () {
        return purchaseProduct;
    }

    public int getPurchaseQuantity () {
        return purchaseQuantity;
    }

    public User getBuyer () {
        return buyer;
    }

    public User getProductOwner () {
        return purchaseProduct.getProductOwner();
    }

    public String urlRedirect ( UriComponentsBuilder builder ) {
        return this.paymentGateway.redirectUrlBuilder(this , builder);
    }

    public PurchaseProcessed process ( PaymentReturn paymentReturn ) {
        if (isPaymentSuccessful()) {
            throw new IllegalStateException("a finished purchase cannot be paid again");
        }

        PaymentStatus paymentStatus = paymentGateway.status(paymentReturn);
        transactions.add(new Payment(paymentReturn.getPaymentId() , paymentStatus));

        return new PurchaseProcessed(this);
    }

    public boolean isPaymentSuccessful () {
        return transactions.stream().anyMatch(Payment::isSuccessful);
    }
}

