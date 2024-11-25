package vn.hoidanit.laptopshop.service.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = StrongPasswordValidator.class) // class nào chịu trách nhiệm
@Target({ ElementType.METHOD, ElementType.FIELD }) // phạm vi sử dụng
@Retention(RetentionPolicy.RUNTIME) // hoạt động
@Documented
public @interface StrongPassword {
    // 3 cái property luôn có của 1 annotation validator
    // thông báo lỗi
    String message() default "Must be 8 characters long and combination of uppercase letters, lowercase letters, numbers, special characters.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
