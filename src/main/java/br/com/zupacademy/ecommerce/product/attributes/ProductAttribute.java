package br.com.zupacademy.ecommerce.product.attributes;

import br.com.zupacademy.ecommerce.product.Product;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class ProductAttribute {

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY )
    private Long id;

    @NotBlank
    @Column ( nullable = false )
    private String attributeName;

    @NotBlank
    @Column ( nullable = false )
    private String attributeDescription;

    @ManyToOne
    @NotNull
    @Valid
    private Product product;

    public ProductAttribute (
            @NotBlank String attributeName ,
            @NotBlank String attributeDescription ,
            @NotNull @Valid Product product ) {
        this.attributeName = attributeName;
        this.attributeDescription = attributeDescription;
        this.product = product;
    }

    @Deprecated
    public ProductAttribute () {
    }

    public Long getId () {
        return id;
    }

    public String getAttributeName () {
        return attributeName;
    }

    public String getAttributeDescription () {
        return attributeDescription;
    }

    @Override public boolean equals ( Object o ) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductAttribute that = (ProductAttribute) o;
        return attributeName.equals(that.attributeName);
    }

    @Override public int hashCode () {
        return Objects.hash(attributeName);
    }

    @Override public String toString () {
        return "ProductAttribute{" +
                "id=" + id +
                ", attributeName='" + attributeName + '\'' +
                ", attributeDescription='" + attributeDescription + '\'' +
                '}';
    }
}
