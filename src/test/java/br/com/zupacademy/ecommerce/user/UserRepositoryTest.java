package br.com.zupacademy.ecommerce.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    void test1(){
        User user = new User("foo@gmail.com" , new UserPasswordClear("123456"));
        repository.save(user);

        String email = "foo@gmail.com";
        User byLogin = repository.findByLogin(email).get();

        assertNotNull(byLogin);
        assertEquals(email, byLogin);
    }
}