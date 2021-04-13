package br.com.zupacademy.ecommerce.purchase;

import br.com.zupacademy.ecommerce.product.Product;
import br.com.zupacademy.ecommerce.purchase.payment.PayPalHandlerRequest;
import br.com.zupacademy.ecommerce.purchase.payment.PaymentGateway;
import br.com.zupacademy.ecommerce.purchase.payment.PaymentGatewayResponse;
import br.com.zupacademy.ecommerce.purchase.payment.PaymentTransaction;
import br.com.zupacademy.ecommerce.user.User;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
    private Set<PaymentTransaction> transactions = new HashSet<>();

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

    /**
     * @assert1 - checks if there is an equal processed transaction
     * @assert2 - checks that there are transactions completed successfully
     */
    public void paymentAttempt ( @Valid PaymentGatewayResponse request ) {
        var paymentTransaction = request.toTransaction(this);

        Set<PaymentTransaction> success = this.transactions.stream()
                .filter(PaymentTransaction::transactionSuccessfullyCompleted)
                .collect(Collectors.toSet());

        Assert.isTrue(!this.transactions.contains(paymentTransaction) , "there is already a same transaction processed! " + paymentTransaction);
        Assert.isTrue(success.isEmpty() , "this purchase already successfully completed!");

        this.transactions.add(paymentTransaction);
    }

    public Set<PaymentTransaction> successfullyProcessedTransactions () {
        Set<PaymentTransaction> successfullyProcessedTransactions = this.transactions.stream()
                .filter(PaymentTransaction::transactionSuccessfullyCompleted)
                .collect(Collectors.toSet());
        Assert.isTrue(successfullyProcessedTransactions.size() <= 1 , "more than one transaction successfully processed, purchase id:" + this.id);
        return successfullyProcessedTransactions;
    }

    public boolean successfullyProcessed () {
        return !successfullyProcessedTransactions().isEmpty();
    }

    public String urlRedirect ( UriComponentsBuilder builder ) {
        return this.paymentGateway.redirectUrlBuilder(this , builder);
    }

    @Override public String toString () {
        return "Purchase{" +
                "id=" + id +
                ", purchaseProduct=" + purchaseProduct +
                ", purchaseQuantity=" + purchaseQuantity +
                ", buyer=" + buyer +
                '}';
    }
}
