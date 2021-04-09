package br.com.zupacademy.ecommerce.product.images;

public class ProductImageResponse {
    private Long id;
    private String link;

    public ProductImageResponse ( ProductImage image ) {
        this.id = image.getId();
        this.link = image.getLink();
    }

    public Long getId () {
        return id;
    }

    public String getLink () {
        return link;
    }
}
