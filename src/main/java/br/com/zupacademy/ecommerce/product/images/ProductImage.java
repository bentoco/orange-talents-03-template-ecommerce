package br.com.zupacademy.ecommerce.product.images;

import br.com.zupacademy.ecommerce.product.Product;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class ProductImage {
    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY )
    private Long id;
    @URL
    @NotBlank
    private String link;
    @ManyToOne
    @NotNull
    @Valid
    private Product product;

    public ProductImage (
            @URL @NotBlank String link ,
            @NotNull @Valid Product product ) {
        this.link = link;
        this.product = product;
    }

    @Deprecated
    public ProductImage () {
    }

    public Long getId () {
        return id;
    }

    public String getLink () {
        return link;
    }

    @Override public boolean equals ( Object o ) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductImage that = (ProductImage) o;
        return Objects.equals(id , that.id) && Objects.equals(link , that.link) && Objects.equals(product , that.product);
    }

    @Override public int hashCode () {
        return Objects.hash(link , product);
    }

    @Override public String toString () {
        return "ProductImage{" +
                "id=" + id +
                ", link='" + link + '\'' +
                '}';
    }
}
