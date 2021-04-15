package br.com.zupacademy.ecommerce.purchase;

import br.com.zupacademy.ecommerce.product.Product;
import br.com.zupacademy.ecommerce.purchase.payment.Payment;
import br.com.zupacademy.ecommerce.purchase.payment.PaymentReturn;
import br.com.zupacademy.ecommerce.purchase.payment.PaymentStatus;
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
    private Set<Payment> paymentTransactions = new HashSet<>();

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

    public User getSeller () {
        return purchaseProduct.getProductOwner();
    }

    public String urlRedirect ( UriComponentsBuilder builder ) {
        return this.paymentGateway.redirectUrl(builder , this);
    }

    public PurchaseProcessed process ( PaymentReturn paymentReturn , Purchase purchase ) {
        if (paymentSucessfully()) {
            throw new IllegalArgumentException("a finished purchase cannot be paid again");
        }
        PaymentStatus status = paymentGateway.status(paymentReturn);
        paymentTransactions.add(new Payment(paymentReturn.getPaymentId() , status , purchase));

        return new PurchaseProcessed(this);
    }

    public boolean paymentSucessfully () {
        return paymentTransactions.stream().anyMatch(Payment::isSuccessful);
    }
}
