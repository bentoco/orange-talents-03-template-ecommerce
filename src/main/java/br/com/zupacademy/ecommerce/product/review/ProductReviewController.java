package br.com.zupacademy.ecommerce.product.review;

import br.com.zupacademy.ecommerce.product.Product;
import br.com.zupacademy.ecommerce.product.ProductRepository;
import br.com.zupacademy.ecommerce.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

//6 pontos de entendimento
@RestController
public class ProductReviewController {

    @PersistenceContext
    private EntityManager manager;

    //1
    @Autowired
    private ProductRepository repository;

    //1
    @PostMapping ( "/api/products/{productId}/reviews" )
    @Transactional
    public ResponseEntity<ProductReviewResponse> createReview (
            @PathVariable Long productId ,
            @RequestBody @Valid ProductReviewRequest request ,
            @AuthenticationPrincipal User user ) {

        //1
        Product product = manager.find(Product.class , productId);
        //1
        User userLoggerd = manager.find(User.class , user.getId());

        //1
        // não é permitido o dono do produto adicionar uma opnião
        if (product.getProductOwner() == userLoggerd) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        //1
        ProductReview productReview = request.toModel(manager , product , userLoggerd);
        manager.persist(productReview);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ProductReviewResponse(productReview));
    }
}
