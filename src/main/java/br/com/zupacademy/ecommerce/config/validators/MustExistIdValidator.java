package br.com.zupacademy.ecommerce.config.validators;

import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class MustExistIdValidator implements ConstraintValidator<MustExistId, Long> {

    private String target;
    private Class<?> klazz;

    @PersistenceContext
    private EntityManager manager;

    @Override public void initialize ( MustExistId params ) {
        this.target = params.target();
        this.klazz = params.klazz();
    }

    @Override public boolean isValid ( Long value , ConstraintValidatorContext context ) {
        if (value != null) {
            String ql = String.format("SELECT 1 FROM %s WHERE %s=:value" , klazz.getName() , target);
            Query query = manager.createQuery(ql);
            query.setParameter("value" , value);
            List<?> result = query.getResultList();
            Assert.isTrue(result.size() <= 1 , "resultado inesperado, existe mais de um registro com o mesmo id");
            return !result.isEmpty();
        } else {
            return true;
        }
    }
}
