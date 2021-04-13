package br.com.zupacademy.ecommerce.purchase.payment;

import br.com.zupacademy.ecommerce.purchase.Purchase;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class PaymentTransaction {

    @Id
    @GeneratedValue ( strategy = IDENTITY )
    private Long id;
    private @Enumerated(EnumType.STRING) PaymentTransactionStatus status;
    private @NotBlank String transactionId;
    private @CreationTimestamp
    LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    private Purchase purchase;

    public PaymentTransaction (
            PaymentTransactionStatus status ,
            @NotBlank String transactionId , Purchase purchase ) {
        this.status = status;
        this.transactionId = transactionId;
        this.purchase = purchase;
    }

    @Deprecated
    public PaymentTransaction () {
    }

    @Override public boolean equals ( Object o ) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentTransaction that = (PaymentTransaction) o;
        return Objects.equals(transactionId , that.transactionId);
    }

    @Override public int hashCode () {
        return Objects.hash(transactionId);
    }

    public boolean transactionSuccessfullyCompleted () {
        return this.status.equals(PaymentTransactionStatus.success);
    }
}
