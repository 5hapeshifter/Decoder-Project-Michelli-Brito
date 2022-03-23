package com.ead.authuser.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameConstraintImpl.class) // Estamos informando a classe que terá a validação
@Target({ElementType.METHOD, ElementType.FIELD}) // Local de uso da validação
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameConstraint {
    // Parametros default do Bean Validation
    String message() default "Invalid username";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};

}
