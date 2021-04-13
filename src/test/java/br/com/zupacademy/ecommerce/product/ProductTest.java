package br.com.zupacademy.ecommerce.product;

import br.com.zupacademy.ecommerce.product.attributes.ProductAttribute;
import br.com.zupacademy.ecommerce.product.category.Category;
import br.com.zupacademy.ecommerce.product.attributes.ProductAttributeRequest;
import br.com.zupacademy.ecommerce.user.User;
import br.com.zupacademy.ecommerce.user.UserPasswordClear;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

class ProductTest {

    private static Stream<Arguments> testGenerator1 () {
        return Stream.of(
                Arguments.of(
                        List.of(
                                // in point
                                new ProductAttributeRequest("key1" , "value1") ,
                                new ProductAttributeRequest("key2" , "value2") ,
                                new ProductAttributeRequest("key3" , "value3"))) ,
                Arguments.of(
                        List.of(
                                // in points
                                new ProductAttributeRequest("key1" , "value1") ,
                                new ProductAttributeRequest("key2" , "value2") ,
                                new ProductAttributeRequest("key3" , "value3") ,
                                new ProductAttributeRequest("key4" , "value4")
                        )
                )
        );

    }


    private static Stream<Arguments> testGenerator2 () {
        return Stream.of(
                Arguments.of(
                        List.of(
                                // off point
                                new ProductAttributeRequest("key1" , "value1") ,
                                new ProductAttributeRequest("key2" , "value2"))) ,
                Arguments.of(
                        List.of(
                                // off points
                                new ProductAttributeRequest("key1" , "value1")))
        );
    }

    @DisplayName ( " product must have at least 3 attributes " )
    @ParameterizedTest
    @MethodSource ( "testGenerator1" )
    void test1 ( Collection<ProductAttributeRequest> attributes ) throws Exception {

        Category category = new Category("category");
        User productOwner = new User("foo@domain.com" , new UserPasswordClear("123456"));

        new Product("name" , BigDecimal.TEN , 10 , "Mussum Ipsum, cacilds vidis litro abertis" , category , productOwner , attributes);
    }

    @DisplayName ( " product must not be created with least 3 attributes " )
    @ParameterizedTest
    @MethodSource ( "testGenerator2" )
    void test2 ( Collection<ProductAttributeRequest> attributes ) throws Exception {
        Category category = new Category("name");
        User productOwner = new User("foo@domain.com" , new UserPasswordClear("123456"));

        Assertions.assertThrows(IllegalArgumentException.class ,
                () -> new Product("name" , BigDecimal.TEN , 10 , "Mussum Ipsum, cacilds vidis litro abertis" , category , productOwner , attributes));
    }

    @DisplayName ( " verify product stock quantity " )
    @ParameterizedTest
    @CsvSource ( {"1,1,true" , "1,2,false" , "4,2,true" , "1,5,false"} )
    void test3 ( int stock , int purchaseQuantity , boolean expected ) {
        List<ProductAttributeRequest> attributes = List.of(
                // off point
                new ProductAttributeRequest("key1" , "value1") ,
                new ProductAttributeRequest("key2" , "value2") ,
                new ProductAttributeRequest("key3" , "value3"));
        Category category = new Category("name");
        User productOwner = new User("foo@domain.com" , new UserPasswordClear("123456"));
        Product
                product =
                new Product("name" , BigDecimal.TEN , stock , "description" , category , productOwner , attributes);

        boolean result = product.reserveIfHasStock(purchaseQuantity);
        Assertions.assertEquals(expected , result);
    }

    @DisplayName ( " must not accepted less than zero " )
    @ParameterizedTest
    @CsvSource ( {"0" , "-1" , "-100"} )
    void test4 ( int stock ) throws Exception {
        List<ProductAttributeRequest> attributes = List.of(
                // off point
                new ProductAttributeRequest("key1" , "value1") ,
                new ProductAttributeRequest("key2" , "value2") ,
                new ProductAttributeRequest("key3" , "value3"));
        Category category = new Category("name");
        User productOwner = new User("foo@domain.com" , new UserPasswordClear("123456"));
        Product
                product =
                new Product("name" , BigDecimal.TEN , 10 , "description" , category , productOwner , attributes);

        Assertions.assertThrows(IllegalArgumentException.class , () -> product.reserveIfHasStock(stock));
    }
}