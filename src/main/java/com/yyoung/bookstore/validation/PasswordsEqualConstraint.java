package com.yyoung.bookstore.validation;

import lombok.Data;
import org.modelmapper.ModelMapper;

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

@Data
class TargetObject {
    private String password;
    private String confirmPassword;
}

class PasswordsEqualConstraintValidator implements
        ConstraintValidator<PasswordsEqualConstraint, Object> {
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public void initialize(PasswordsEqualConstraint arg0) {
    }

    @Override
    public boolean isValid(Object candidate, ConstraintValidatorContext arg1) {
        TargetObject user = modelMapper.map(candidate, TargetObject.class);
        return user.getPassword().equals(user.getConfirmPassword());
    }
}
