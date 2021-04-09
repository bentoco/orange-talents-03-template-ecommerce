package br.com.zupacademy.ecommerce.product;

import br.com.zupacademy.ecommerce.config.uploader.StorageProviderFake;
import br.com.zupacademy.ecommerce.config.validators.ProductAttributeNameMustBeUniqueValidator;
import br.com.zupacademy.ecommerce.product.images.ProductImageRequest;
import br.com.zupacademy.ecommerce.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping ( "/api/products" )
public class ProductController {
    // total 7 pontos de cargas cognitivas

    @PersistenceContext
    private EntityManager manager;

    //1
    @Autowired
    private StorageProviderFake storage;

    //1
    @Autowired
    private ProductRepository repository;

    @InitBinder ( value = "productRequest" )
    public void init ( WebDataBinder webDataBinder ) {
        //1
        webDataBinder.addValidators(new ProductAttributeNameMustBeUniqueValidator());
    }

    @PostMapping
    @Transactional
    //1
    public ResponseEntity<?> createProduct (
            @RequestBody @Valid ProductRequest request ,
            @AuthenticationPrincipal User user ) {
        //1
        Product product = request.toModel(manager , user);
        manager.persist(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(product.getId());
    }

    @PostMapping ( "/{productId}/images" )
    @Transactional
    //1
    public String uploadImage (
            @PathVariable Long productId ,
            @Valid ProductImageRequest request ,
            @AuthenticationPrincipal User user ) {

        Set<String> links = storage.save(request.getImages());
        Product product = manager.find(Product.class , productId);

        if (!product.productIsHisOwn(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        product.setImagesToProduct(links);
        manager.merge(product);
        return product.toString();
    }

    //1
    @GetMapping ( "/list" )
    public List<ProductResponse> allProducts () {
        List<Product> products = repository.findAll();
        return ProductResponse.toDto(products);
    }
}
