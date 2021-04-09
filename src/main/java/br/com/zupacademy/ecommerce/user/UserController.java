package br.com.zupacademy.ecommerce.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

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

    @GetMapping("list")
    public List<UserResponse> allUsers(){
        List<User> users = repository.findAll();
        return UserResponse.toDto(users);
    }
}
