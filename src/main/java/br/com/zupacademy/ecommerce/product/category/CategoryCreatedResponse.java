package br.com.zupacademy.ecommerce.product.category;

public class CategoryCreatedResponse {

    private String categoryName;
    private Category mainCategory;

    public CategoryCreatedResponse ( Category category ) {
        this.categoryName = category.getCategoryName();
        this.mainCategory = category.getIdMainCategory();
    }

    public String getCategoryName () {
        return categoryName;
    }

    public Category getMainCategory () {
        return mainCategory;
    }
}
