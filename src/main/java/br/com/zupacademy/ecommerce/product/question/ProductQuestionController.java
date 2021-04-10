package br.com.zupacademy.ecommerce.product.question;

import br.com.zupacademy.ecommerce.config.mailer.MailerManager;
import br.com.zupacademy.ecommerce.product.Product;
import br.com.zupacademy.ecommerce.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class ProductQuestionController {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private MailerManager mailerManager;

    @PostMapping ( "/api/products/{productId}/questions" )
    @Transactional
    public ResponseEntity<ProductQuestionResponse> createQuestion (
            @PathVariable Long productId ,
            @RequestBody @Valid ProductQuestionRequest request ,
            @AuthenticationPrincipal User user ) {
        Product product = manager.find(Product.class , productId);
        ProductQuestion question = request.toModel(product , user);
        manager.persist(question);
        mailerManager.newQuestion(question);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ProductQuestionResponse(question));
    }
}
