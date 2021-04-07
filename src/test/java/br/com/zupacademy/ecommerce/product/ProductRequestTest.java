package br.com.zupacademy.ecommerce.product;

import br.com.zupacademy.ecommerce.product.attributes.ProductAttributeRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

class ProductRequestTest {

    private static Stream<Arguments> testGenerator1 () {
        return Stream.of(
                Arguments.of(0 , List.of()) ,
                Arguments.of(0 , List.of(new ProductAttributeRequest("key1" , "value1"))) ,
                Arguments.of(0 , List.of(new ProductAttributeRequest("key1" , "value1") , new ProductAttributeRequest("key2" , "value2"))) ,
                Arguments.of(1 , List.of(new ProductAttributeRequest("key1" , "value1") , new ProductAttributeRequest("key1" , "value1")))
        );
    }

    @DisplayName ( " should create many products with multiples attributes types" )
    @ParameterizedTest
    @MethodSource ( "testGenerator1" )
    void test1 ( int expectedResult , List<ProductAttributeRequest> attributeRequests ) {
        ProductRequest product =
                new ProductRequest("name" , BigDecimal.TEN , 10 , "Mussum Ipsum, cacilds vidis litro abertis" , 1L , attributeRequests);
        Assertions.assertEquals(expectedResult , product.getEqualsAttributes().size());
    }

}