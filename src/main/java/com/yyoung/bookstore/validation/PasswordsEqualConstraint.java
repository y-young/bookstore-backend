package com.yyoung.bookstore.validation;

import com.yyoung.bookstore.dto.NewUser;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordsEqualConstraintValidator.class)
@Documented
public @interface PasswordsEqualConstraint {
    String message() default "两次输入的密码不一致";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

class PasswordsEqualConstraintValidator implements
        ConstraintValidator<PasswordsEqualConstraint, Object> {

    @Override
    public void initialize(PasswordsEqualConstraint arg0) {
    }

    @Override
    public boolean isValid(Object candidate, ConstraintValidatorContext arg1) {
        NewUser user = (NewUser) candidate;
        return user.getPassword().equals(user.getConfirmPassword());
    }
}
