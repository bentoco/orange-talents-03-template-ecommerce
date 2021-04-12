package br.com.zupacademy.ecommerce.product.details;

import br.com.zupacademy.ecommerce.product.Product;
import br.com.zupacademy.ecommerce.product.attributes.ProductAttributeResponse;
import br.com.zupacademy.ecommerce.product.images.ProductImage;
import br.com.zupacademy.ecommerce.product.question.ProductQuestion;
import br.com.zupacademy.ecommerce.product.review.ProductReviewDetails;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductDetailsResponse {
    private String name;
    private String description;
    private BigDecimal price;
    private Set<ProductAttributeResponse> attributes = new HashSet<>();
    private Set<String> imagesLinks;
    private Set<String> questions;
    private Set<Map<String, String>> reviews;
    private double ratingAvarage;
    private int total;

    public ProductDetailsResponse ( Product product ) {
        this.name = product.getProductName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.attributes =
                product.getAttributes().stream().map(ProductAttributeResponse::new).collect(Collectors.toSet());
        this.imagesLinks = product.mapImages(ProductImage::getLink);
        this.questions = product.mapQuestions(ProductQuestion::getQuestionTitle);

        ProductReviewDetails reviews = product.getReviews();
        this.reviews = reviews.mapReviews(r -> {
            return Map.of("title" , r.getReviewTitle() , "description" , r.getReviewDescription());
        });
        this.ratingAvarage = reviews.avarage();
        this.total = reviews.total();
    }

    public String getName () {
        return name;
    }

    public String getDescription () {
        return description;
    }

    public BigDecimal getPrice () {
        return this.price;
    }

    public Set<ProductAttributeResponse> getAttributes () {
        return attributes;
    }

    public Set<String> getImagesLinks () {
        return imagesLinks;
    }

    public Set<String> getQuestions () {
        return questions;
    }

    public Set<Map<String, String>> getReviews () {
        return reviews;
    }

    public double getRatingAvarage () {
        return this.ratingAvarage;
    }

    public int getTotal () {
        return total;
    }
}
