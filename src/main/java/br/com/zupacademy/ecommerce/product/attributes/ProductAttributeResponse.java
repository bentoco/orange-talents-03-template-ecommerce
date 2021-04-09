package br.com.zupacademy.ecommerce.product.attributes;

public class ProductAttributeResponse {
    private Long id;
    private String attributeName;
    private String description;

    public ProductAttributeResponse ( ProductAttribute attribute ) {
        this.id = attribute.getId();
        this.attributeName = attribute.getAttributeName();
        this.description = attribute.getAttributeDescription();
    }

    public Long getId () {
        return id;
    }

    public String getAttributeName () {
        return attributeName;
    }

    public String getDescription () {
        return description;
    }
}
