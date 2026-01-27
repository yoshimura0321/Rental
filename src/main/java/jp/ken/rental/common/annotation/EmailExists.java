package jp.ken.rental.common.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jp.ken.rental.common.validator.EmailExistsValidator;

@Retention(RUNTIME)
@Target(FIELD)
@Constraint(validatedBy = EmailExistsValidator.class)
public @interface EmailExists {
	
	String message();
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default{};
}
