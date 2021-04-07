package br.com.zupacademy.ecommerce.config.validators;

import br.com.zupacademy.ecommerce.product.ProductRequest;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Set;

public class ProductAttributeNameMustBeUniqueValidator implements Validator {
    @Override public boolean supports ( Class<?> klazz ) {
        // validando se a classe é ela mesma ou filha
        return ProductRequest.class.isAssignableFrom(klazz);
    }

    @Override public void validate ( Object target , Errors errors ) {
        if (errors.hasErrors()) {
            return;
        }

        ProductRequest request = (ProductRequest) target;
        Set<String> equalNames = request.getEqualsAttributes();
        if (!equalNames.isEmpty()) {
            errors.rejectValue("attributes" ,
                    null ,
                    "você tem caracteríticas iguais " + equalNames);
        }
    }
}
