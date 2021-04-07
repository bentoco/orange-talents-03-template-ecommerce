package br.com.zupacademy.ecommerce.product;

import br.com.zupacademy.ecommerce.category.Category;
import br.com.zupacademy.ecommerce.product.attributes.ProductAttribute;
import br.com.zupacademy.ecommerce.product.attributes.ProductAttributeRequest;
import br.com.zupacademy.ecommerce.user.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Product {

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY )
    private Long id;

    @NotBlank
    @Column ( nullable = false, unique = true )
    private String productName;

    @NotNull
    @Positive
    @Column ( nullable = false )
    private BigDecimal price;

    @NotNull
    @PositiveOrZero
    @Column ( nullable = false )
    private Integer stockQuantity;

    @NotBlank
    @Length ( max = 1000 )
    @Column ()
    private String description;

    @NotNull
    @Valid
    @ManyToOne
    private Category category;

    @NotNull
    @Valid
    @ManyToOne
    private User productOwner;

    @CreationTimestamp
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany ( mappedBy = "product", cascade = CascadeType.PERSIST )
    private Set<ProductAttribute> attributes = new HashSet<>();

    public Product (
            @NotBlank String productName ,
            @NotNull @Positive BigDecimal price ,
            @NotNull @PositiveOrZero Integer stockQuantity ,
            @NotBlank @Length ( max = 1000 ) String description ,
            @NotNull @Valid Category category ,
            @NotNull @Valid User productOwner ,
            @Size ( min = 3 ) @Valid Collection<ProductAttributeRequest> attributes ) {
        this.productName = productName;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.description = description;
        this.category = category;
        this.productOwner = productOwner;
        this.attributes.addAll(attributes.stream().map(attribute -> attribute.toModel(this)).collect(Collectors.toSet()));
        Assert.isTrue(this.attributes.size() >= 3 , "produto deve conter no mínimo 3 características");
    }

    @Deprecated
    public Product () {
    }

    public Long getId () {
        return id;
    }

    public String getProductName () {
        return productName;
    }

    public BigDecimal getPrice () {
        return price;
    }

    public String getDescription () {
        return description;
    }

    public User getProductOwner () {
        return productOwner;
    }

    @Override public boolean equals ( Object o ) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productName.equals(product.productName);
    }

    @Override public int hashCode () {
        return Objects.hash(productName);
    }
}
