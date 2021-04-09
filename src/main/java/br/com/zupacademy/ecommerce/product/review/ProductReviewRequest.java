package br.com.zupacademy.ecommerce.product.review;

import br.com.zupacademy.ecommerce.product.Product;
import br.com.zupacademy.ecommerce.user.User;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.validation.constraints.*;

public class ProductReviewRequest {

    @NotBlank
    @Length ( max = 50 )
    private String reviewTitle;
    @NotBlank
    @Length ( max = 500 )
    private String reviewDescription;
    @NotNull
    @Min ( 1 )
    @Max ( 5 )
    private Integer reviewRating;

    public ProductReviewRequest (
            @NotBlank @Length ( max = 50 ) String reviewTitle ,
            @NotBlank @Length ( max = 500 ) String reviewDescription ,
            @NotNull @Min ( 1 ) @Max ( 5 ) Integer reviewRating ) {
        this.reviewTitle = reviewTitle;
        this.reviewDescription = reviewDescription;
        this.reviewRating = reviewRating;
    }

    public ProductReview toModel ( EntityManager manager , Product product , User user ) {
        Assert.notNull(product , "o produto com o id informado não foi encontrado");
        Assert.notNull(user , "usuário não existe");
        return new ProductReview(reviewTitle , reviewDescription , reviewRating , user , product);
    }

}
