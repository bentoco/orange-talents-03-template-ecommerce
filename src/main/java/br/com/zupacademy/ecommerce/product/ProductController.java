package br.com.zupacademy.ecommerce.product;

import br.com.zupacademy.ecommerce.config.validators.ProductAttributeNameMustBeUniqueValidator;
import br.com.zupacademy.ecommerce.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping ( "/api/products" )
public class ProductController {

    @PersistenceContext
    private EntityManager manager;

    @InitBinder
    public void init ( WebDataBinder webDataBinder ) {
        webDataBinder.addValidators(new ProductAttributeNameMustBeUniqueValidator());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> createProduct (
            @RequestBody @Valid ProductRequest request ,
            @AuthenticationPrincipal User user ) {
        Product product = request.toModel(manager , user);
        manager.persist(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(product.getId());
    }
}
