package br.com.zupacademy.ecommerce.product;

import br.com.zupacademy.ecommerce.product.attributes.ProductAttributeResponse;
import br.com.zupacademy.ecommerce.product.images.ProductImageResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ProductResponse {

    private Long id;
    private String productName;
    private BigDecimal price;
    private Integer stockQuantity;
    private String description;
    private String category;
    private Long productOwner;
    private LocalDateTime createdAt;
    private Set<ProductAttributeResponse> attributes;
    private Set<ProductImageResponse> images;

    public ProductResponse ( Product product ) {
        this.id = product.getId();
        this.productName = product.getProductName();
        this.price = product.getPrice();
        this.stockQuantity = product.getStockQuantity();
        this.description = product.getDescription();
        this.category = product.getCategory().getCategoryName();
        this.productOwner = product.getProductOwner().getId();
        this.createdAt = product.getCreatedAt();
        this.attributes = new HashSet<>();
        this.attributes.addAll(product.getAttributes().stream().map(ProductAttributeResponse::new).collect(Collectors.toSet()));
        this.images = new HashSet<>();
        this.images.addAll(product.getImages().stream().map(ProductImageResponse::new).collect(Collectors.toSet()));
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

    public Integer getStockQuantity () {
        return stockQuantity;
    }

    public String getDescription () {
        return description;
    }

    public String getCategory () {
        return category;
    }

    public Long getProductOwner () {
        return productOwner;
    }

    public LocalDateTime getCreatedAt () {
        return createdAt;
    }

    public Set<ProductAttributeResponse> getAttributes () {
        return attributes;
    }

    public Set<ProductImageResponse> getImages () {
        return images;
    }

    public static List<ProductResponse> toDto ( List<Product> products ) {
        return products.stream().map(ProductResponse::new).collect(Collectors.toList());
    }
}
