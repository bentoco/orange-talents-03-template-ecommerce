package br.com.zupacademy.ecommerce.config.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target ( {FIELD} )
@Retention ( RUNTIME )
@Constraint ( validatedBy = MustExistIdValidator.class )
public @interface MustExistId {
    String message () default "id informado não existe";

    Class<?>[] groups () default {};

    Class<? extends Payload>[] payload () default {};

    String target ();

    Class<?> klazz ();
}
