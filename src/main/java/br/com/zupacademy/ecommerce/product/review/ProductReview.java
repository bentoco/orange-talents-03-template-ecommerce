package br.com.zupacademy.ecommerce.product.review;

import br.com.zupacademy.ecommerce.product.Product;
import br.com.zupacademy.ecommerce.user.User;

import javax.persistence.*;

@Entity
public class ProductReview {

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY )
    private Long id;
    @Column ( nullable = false )
    private String reviewTitle;
    @Column ( nullable = false )
    private String reviewDescription;
    @Column ( nullable = false )
    private Integer reviewRating;

    @ManyToOne
    private User productOwner;
    @ManyToOne
    private Product product;

    public ProductReview (
            String reviewTitle ,
            String reviewDescription ,
            Integer reviewRating ,
            User productOwner , Product product ) {
        this.reviewTitle = reviewTitle;
        this.reviewDescription = reviewDescription;
        this.reviewRating = reviewRating;
        this.productOwner = productOwner;
        this.product = product;
    }

    @Deprecated
    public ProductReview () {
    }

    public Long getId () {
        return id;
    }

    public String getReviewTitle () {
        return reviewTitle;
    }

    public String getReviewDescription () {
        return reviewDescription;
    }

    public Integer getReviewRating () {
        return reviewRating;
    }

    public User getProductOwner () {
        return productOwner;
    }

    public Product getProduct () {
        return product;
    }

    @Override public String toString () {
        return "ProductReview{" +
                "reviewId=" + id +
                ", reviewTitle='" + reviewTitle + '\'' +
                ", reviewDescription='" + reviewDescription + '\'' +
                ", reviewRating=" + reviewRating +
                ", productOwner=" + productOwner +
                ", product=" + product +
                '}';
    }

}
