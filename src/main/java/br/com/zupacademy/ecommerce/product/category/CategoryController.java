package br.com.zupacademy.ecommerce.product.category;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping ( "/api/categories" )
public class CategoryController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping
    @Transactional
    public ResponseEntity<CategoryCreatedResponse> createCategory ( @RequestBody @Valid CategoryRequest request ) {
        Category category = request.toModel(manager);
        manager.persist(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CategoryCreatedResponse(category));
    }
}
