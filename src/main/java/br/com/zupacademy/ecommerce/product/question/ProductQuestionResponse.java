package br.com.zupacademy.ecommerce.product.question;

import java.time.LocalDateTime;

public class ProductQuestionResponse {
    private Long questionId;
    private String questionTitle;
    private Long productOwnder;
    private Long productId;
    private LocalDateTime createAt;

    public ProductQuestionResponse ( ProductQuestion question ) {
        this.questionId = question.getId();
        this.questionTitle = question.getQuestionTitle();
        this.productOwnder = question.getUser().getId();
        this.productId = question.getProduct().getId();
        this.createAt = question.getCreatedAt();
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

    public LocalDateTime getCreateAt () {
        return createAt;
    }
}
