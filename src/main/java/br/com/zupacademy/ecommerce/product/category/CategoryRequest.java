package br.com.zupacademy.ecommerce.product.category;

import br.com.zupacademy.ecommerce.config.validators.MustBeUnique;
import br.com.zupacademy.ecommerce.config.validators.MustExistId;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class CategoryRequest {

    @NotBlank
    @MustBeUnique ( field = "categoryName", klazz = Category.class )
    private String categoryName;

    @Positive
    @MustExistId ( target = "id", klazz = Category.class )
    private Long idMainCategory;

    public void setCategoryName ( @NotBlank String categoryName ) {
        this.categoryName = categoryName;
    }

    public void setIdMainCategory ( Long idMainCategory ) {
        this.idMainCategory = idMainCategory;
    }

    public Category toModel ( EntityManager manager ) {
        Category category = new Category(categoryName);
        if (idMainCategory != null) {
            Category idExist = manager.find(Category.class , idMainCategory);
            Assert.notNull(idMainCategory , "id da categoria deve ser válido");
            category.setIdMainCategory(idExist);
        }
        return category;
    }
}
