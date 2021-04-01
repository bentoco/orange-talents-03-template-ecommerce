package br.com.zupacademy.ecommerce.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping ( "/api/users" )
public class UserController {

    @Autowired
    private final UserRepository repository;

    public UserController ( UserRepository repository ) {
        this.repository = repository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<UserCreatedResponse> createUser ( @Valid @RequestBody UserRequest request ) {
        User user = request.toModel();
        repository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserCreatedResponse());
    }
}
