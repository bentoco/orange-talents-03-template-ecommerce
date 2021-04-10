package br.com.zupacademy.ecommerce.product.question;

import br.com.zupacademy.ecommerce.product.Product;
import br.com.zupacademy.ecommerce.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class ProductQuestion {

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY )
    private Long id;

    @NotBlank
    private String questionTitle;

    @ManyToOne
    private User user;

    @ManyToOne
    private Product product;

    public ProductQuestion ( @NotBlank String questionTitle , User user , Product product ) {
        this.questionTitle = questionTitle;
        this.user = user;
        this.product = product;
    }

    @Deprecated
    public ProductQuestion () {

    }

    public Long getId () {
        return id;
    }

    public String getQuestionTitle () {
        return questionTitle;
    }

    public User getUser () {
        return user;
    }

    public Product getProduct () {
        return product;
    }

    @Override public String toString () {
        return "ProductQuestion{" +
                "id=" + id +
                ", questionTitle='" + questionTitle + '\'' +
                ", user=" + user +
                ", product=" + product +
                '}';
    }
}
