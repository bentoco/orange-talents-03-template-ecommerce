package br.com.zupacademy.ecommerce.product.question;

import br.com.zupacademy.ecommerce.product.Product;
import br.com.zupacademy.ecommerce.user.User;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.Assert;

import javax.validation.constraints.NotBlank;

public class ProductQuestionRequest {

    @NotBlank
    private String questionTitle;

    @JsonCreator
    public ProductQuestionRequest ( @JsonProperty ( "questionTitle" ) @NotBlank String questionTitle ) {
        this.questionTitle = questionTitle;
    }

    @Override public String toString () {
        return "ProductQuestionRequest{" +
                "questionTitle='" + questionTitle + '\'' +
                '}';
    }

    public ProductQuestion toModel ( Product product , User user ) {
        Assert.notNull(product , "produto não pode ser nulo");
        Assert.notNull(user , "usuario não pode ser nulo");

        return new ProductQuestion(questionTitle , user , product);
    }
}
