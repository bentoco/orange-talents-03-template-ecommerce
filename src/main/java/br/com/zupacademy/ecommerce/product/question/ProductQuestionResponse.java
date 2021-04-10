package br.com.zupacademy.ecommerce.product.question;

public class ProductQuestionResponse {
    private Long questionId;
    private String questionTitle;
    private Long productOwnder;
    private Long productId;

    public ProductQuestionResponse ( ProductQuestion question ) {
        this.questionId = question.getId();
        this.questionTitle = question.getQuestionTitle();
        this.productOwnder = question.getUser().getId();
        this.productId = question.getProduct().getId();
    }

    public Long getQuestionId () {
        return questionId;
    }

    public String getQuestionTitle () {
        return questionTitle;
    }

    public Long getProductOwnder () {
        return productOwnder;
    }

    public Long getProductId () {
        return productId;
    }
}
