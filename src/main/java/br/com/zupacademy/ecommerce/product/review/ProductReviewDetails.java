package br.com.zupacademy.ecommerce.product.review;

import java.util.OptionalDouble;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ProductReviewDetails {
    private Set<ProductReview> reviews;

    public ProductReviewDetails ( Set<ProductReview> reviews ) {
        this.reviews = reviews;
    }

    public <T> Set<T> mapReviews ( Function<ProductReview, T> mapper ) {
        return this.reviews.stream().map(mapper).collect(Collectors.toSet());
    }

    public double avarage(){
        Set<Integer> rates = mapReviews(ProductReview::getReviewRating);
        OptionalDouble possibleAvarage = rates.stream().mapToInt(rate -> rate).average();
        return possibleAvarage.orElse(0.0);
    }

    public int total () {
        return this.reviews.size();
    }
}
