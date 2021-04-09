package br.com.zupacademy.ecommerce.product.review;

public class ProductReviewResponse {
    private Long id;
    private String reviewTitle;
    private String reviewDescription;
    private Integer reviewRating;
    private Long productOwnerId;
    private Long productId;

    public ProductReviewResponse (
            ProductReview review ) {
        this.id = review.getId();
        this.reviewTitle = review.getReviewTitle();
        this.reviewDescription = review.getReviewDescription();
        this.reviewRating = review.getReviewRating();
        this.productOwnerId = review.getProductOwner().getId();
        this.productId = review.getProduct().getId();
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

    public Long getProductOwnerId () {
        return productOwnerId;
    }

    public Long getProductId () {
        return productId;
    }
}
