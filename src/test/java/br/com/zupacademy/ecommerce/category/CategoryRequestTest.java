package br.com.zupacademy.ecommerce.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.persistence.EntityManager;

class CategoryRequestTest {

    private EntityManager manager;

    @BeforeEach
    void init () {
        manager = Mockito.mock(EntityManager.class);
    }

    @Test
    @DisplayName ( "should create a new category without the main" )
    void test1 () throws Exception {
        CategoryRequest request = new CategoryRequest();
        request.setCategoryName("name");

        Mockito.verify(manager , Mockito.never())
                .find(Mockito.eq(Category.class) , Mockito.anyLong());
    }

    @Test
    @DisplayName ( "should create a new category with the main" )
    void test2 () throws Exception {
        CategoryRequest request = new CategoryRequest();
        request.setCategoryName("name");
        request.setIdMainCategory(1L);

        Category mainCategory = new Category("test");

        Mockito.when(manager.find(Category.class , 1L)).thenReturn(mainCategory);
        request.toModel(manager);

        Mockito.verify(manager).find(Category.class , 1L);
    }
}