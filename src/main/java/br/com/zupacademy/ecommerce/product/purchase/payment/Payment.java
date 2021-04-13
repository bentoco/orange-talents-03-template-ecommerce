package br.com.zupacademy.ecommerce.product.purchase.payment;

import br.com.zupacademy.ecommerce.product.purchase.Purchase;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

import static br.com.zupacademy.ecommerce.product.purchase.payment.PaymentStatus.SUCCESS;
import static java.time.LocalDateTime.now;

@Entity
public class Payment {

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY )
    private Long id;

    @NotBlank
    private String paymentId;

    @Enumerated ( EnumType.STRING )
    private PaymentStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt = now();

    @ManyToOne
    private Purchase purchase;


    @Deprecated
    private Payment () {
    }

    public Payment (
            @NotBlank String paymentId ,
            PaymentStatus status ,
            Purchase purchase ) {
        this.paymentId = paymentId;
        this.status = status;
        this.purchase = purchase;
    }

    public Payment ( String paymentId , PaymentStatus status ) {
        this.paymentId = paymentId;
        this.status = status;
    }

    public PaymentStatus getStatus () {
        return status;
    }

    public LocalDateTime getCreatedAt () {
        return createdAt;
    }

    public boolean isSuccessful () {
        return SUCCESS == status;
    }
}
