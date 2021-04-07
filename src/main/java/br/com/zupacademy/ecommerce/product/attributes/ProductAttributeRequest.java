package br.com.zupacademy.ecommerce.product.attributes;

import br.com.zupacademy.ecommerce.product.Product;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProductAttributeRequest {

    @NotBlank
    private String attributeName;

    @NotBlank
    private String attributeDescription;

    public ProductAttributeRequest (
            @NotBlank String attributeName ,
            @NotBlank String attributeDescription ) {
        this.attributeName = attributeName;
        this.attributeDescription = attributeDescription;
    }

    public String getAttributeName () {
        return attributeName;
    }

    public String getAttributeDescription () {
        return attributeDescription;
    }

    public ProductAttribute toModel ( @NotNull @Valid Product product ) {
        return new ProductAttribute(attributeName , attributeDescription , product);
    }
}
