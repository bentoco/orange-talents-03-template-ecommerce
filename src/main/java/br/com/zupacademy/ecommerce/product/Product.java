package br.com.zupacademy.ecommerce.product;

import br.com.zupacademy.ecommerce.product.category.Category;
import br.com.zupacademy.ecommerce.product.attributes.ProductAttribute;
import br.com.zupacademy.ecommerce.product.attributes.ProductAttributeRequest;
import br.com.zupacademy.ecommerce.product.images.ProductImage;
import br.com.zupacademy.ecommerce.product.purchase.Purchase;
import br.com.zupacademy.ecommerce.product.purchase.PurchaseRequest;
import br.com.zupacademy.ecommerce.product.question.ProductQuestion;
import br.com.zupacademy.ecommerce.product.review.ProductReview;
import br.com.zupacademy.ecommerce.product.review.ProductReviewDetails;
import br.com.zupacademy.ecommerce.user.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
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
    //1
    private Category category;

    @NotNull
    @Valid
    @ManyToOne
    //1
    private User productOwner;

    @CreationTimestamp
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany ( mappedBy = "product", cascade = CascadeType.PERSIST )
    //1
    private Set<ProductAttribute> attributes = new HashSet<>();

    @OneToMany ( mappedBy = "product", cascade = CascadeType.MERGE )
    //1
    private Set<ProductImage> images = new HashSet<>();

    @OneToMany ( mappedBy = "product", cascade = CascadeType.MERGE )
    //1
    private Set<ProductReview> productReviews = new HashSet<>();

    @OneToMany ( mappedBy = "product" )
    private Set<ProductQuestion> questions = new HashSet<>();

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
        //1
        this.attributes.addAll(attributes.stream().map(attribute -> attribute.toModel(this)).collect(Collectors.toSet()));
        //1
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

    public Integer getStockQuantity () {
        return stockQuantity;
    }

    public Category getCategory () {
        return category;
    }

    public LocalDateTime getCreatedAt () {
        return createdAt;
    }

    public Set<ProductAttribute> getAttributes () {
        return attributes;
    }

    public Set<ProductImage> getImages () {
        return images;
    }

    public Set<ProductQuestion> getQuestions () {
        return questions;
    }

    public Set<ProductReview> getProductReviews () {
        return productReviews;
    }

    public ProductReviewDetails getReviews () {
        return new ProductReviewDetails(this.productReviews);
    }

    public void setImagesToProduct ( Set<String> links ) {
        Set<ProductImage>
                images =
                links.stream().map(link -> new ProductImage(link , this)).collect(Collectors.toSet());
        this.images.addAll(images);
    }

    public <T> Set<T> mapImages ( Function<ProductImage, T> mapper ) {
        return this.images.stream().map(mapper).collect(Collectors.toSet());
    }

    public <T> Set<T> mapQuestions ( Function<ProductQuestion, T> mapper ) {
        return this.questions.stream().map(mapper).collect(Collectors.toSet());
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

    public boolean productIsHisOwn ( User possibleProductOwner ) {
        return this.getProductOwner().getLogin().equals(possibleProductOwner.getLogin());
    }

    @Override public String toString () {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", productOwner=" + productOwner +
                ", createdAt=" + createdAt +
                ", attributes=" + attributes +
                ", images=" + images +
                '}';
    }

    /**
     * changes the product {@link #stockQuantity}
     *
     * @param request a new Purchase (this parameter is highly coupled, but if needed an interface can be created to decouple)
     * @param buyer   a buyer
     * @return An {@link Optional<Purchase>} with a new {@link Purchase} if stock quantity is valid
     */
    public Optional<Purchase> reserveIfHasStock ( PurchaseRequest request , User buyer ) {
        if (stockQuantity < request.getQuantity()) {
            return Optional.empty();
        }
        stockQuantity -= request.getQuantity();

        return Optional.of(request.toPurchase(buyer , this));
    }
}
