package br.com.zupacademy.ecommerce.product.purchase;

import br.com.zupacademy.ecommerce.product.Product;
import br.com.zupacademy.ecommerce.user.User;
import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

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

    @Enumerated(EnumType.STRING)
    @NotNull
    private PaymentGateway paymentGateway;

    @ManyToOne
    @NotNull
    @Valid
    private User buyer;

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

    @Override public String toString () {
        return "Purchase{" +
                "id=" + id +
                ", purchaseProduct=" + purchaseProduct +
                ", purchaseQuantity=" + purchaseQuantity +
                ", buyer=" + buyer +
                '}';
    }
}
