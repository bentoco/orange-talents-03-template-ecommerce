package br.com.zupacademy.ecommerce.product;

import br.com.zupacademy.ecommerce.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
     boolean existsByProductOwner ( User user );
}
