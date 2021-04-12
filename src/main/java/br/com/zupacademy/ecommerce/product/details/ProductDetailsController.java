package br.com.zupacademy.ecommerce.product.details;

import br.com.zupacademy.ecommerce.product.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RestController
public class ProductDetailsController {

    @PersistenceContext
    private EntityManager manager;

    @GetMapping ( "api/products/{productId}" )
    public ResponseEntity<ProductDetailsResponse> readProduct ( @PathVariable Long productId ) {
        Product product = manager.find(Product.class , productId);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new ProductDetailsResponse(product));
    }
}
