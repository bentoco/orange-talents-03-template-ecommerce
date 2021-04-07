package br.com.zupacademy.ecommerce.product;

import br.com.zupacademy.ecommerce.category.Category;
import br.com.zupacademy.ecommerce.config.validators.MustBeUnique;
import br.com.zupacademy.ecommerce.config.validators.MustExistId;
import br.com.zupacademy.ecommerce.product.attributes.ProductAttributeRequest;
import br.com.zupacademy.ecommerce.user.User;
import io.jsonwebtoken.lang.Assert;
import org.hibernate.validator.constraints.Length;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductRequest {

    @NotBlank
    @MustBeUnique ( field = "productName", klazz = Product.class )
    private String productName;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    @PositiveOrZero
    private Integer stockQuantity;

    @NotBlank
    @Length ( max = 1000 )
    private String description;

    @NotNull
    @MustExistId ( target = "id", klazz = Category.class )
    private Long idCategory;

    @Size ( min = 3 )
    @Valid
    private List<ProductAttributeRequest> attributes = new ArrayList<>();

    public ProductRequest (
            @NotBlank String productName ,
            @NotNull @Positive BigDecimal price ,
            @NotNull @PositiveOrZero Integer stockQuantity ,
            @NotBlank @Length ( max = 1000 ) String description ,
            @NotNull Long idCategory ,
            List<ProductAttributeRequest> attributes ) {
        this.productName = productName;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.description = description;
        this.idCategory = idCategory;
        this.attributes.addAll(attributes);
    }

    public List<ProductAttributeRequest> getAttributes () {
        return attributes;
    }

    public void setAttributes ( List<ProductAttributeRequest> attributes ) {
        this.attributes = attributes;
    }

    public Product toModel ( EntityManager manager , User userId ) {
        Category category = manager.find(Category.class , idCategory);
        User productOwner = manager.find(User.class , userId.getId());

        Assert.isTrue(category != null , "id categoria não existe");
        Assert.isTrue(productOwner != null , "id usuário não existe");

        return new Product(productName , price , stockQuantity , description , category , productOwner , attributes);
    }

    /*
    Aqui validamosa a possibilidade de adicionar o nome da característica em um HashSet,
    que por padrão possui métodos de comparação que não permite valores duplicados.
    Para facilitar o entendimento do erro, é validado se a lista de nome iguais está
    populada, se estiver é retornado a msg de erro junto ao nome da caracteristica
     */
    public Set<String> getEqualsAttributes () {
        HashSet<String> equalsNames = new HashSet<>();
        HashSet<String> results = new HashSet<>();
        for (ProductAttributeRequest attribute : attributes) {
            String name = attribute.getAttributeName();
            if (!equalsNames.add(name)) {
                results.add(name);
            }
        }
        return results;
    }
}
