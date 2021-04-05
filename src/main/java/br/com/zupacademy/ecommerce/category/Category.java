package br.com.zupacademy.ecommerce.category;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Category {

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY )
    private Long id;

    @Column ( nullable = false, unique = true )
    private String categoryName;

    @ManyToOne
    @JoinColumn ( name = "main_categoria_id", foreignKey = @ForeignKey ( name = "MAIN_CATEGORY_ID_FK" ) )
    private Category idMainCategory;

    public Category ( @NotBlank String categoryName ) {
        this.categoryName = categoryName;
    }

    @Deprecated
    public Category () {
    }

    public void setIdMainCategory ( Category idMainCategory ) {
        this.idMainCategory = idMainCategory;
    }

    public String getCategoryName () {
        return categoryName;
    }

    public Category getIdMainCategory () {
        return idMainCategory;
    }

    @Override public String toString () {
        return "Category{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                ", idMainCategory=" + idMainCategory +
                '}';
    }
}
