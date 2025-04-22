package com.gpoint.files_service.annotation.image;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.springframework.http.MediaType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Image.List.class)
@Constraint(validatedBy = { ImageValidator.class})
public @interface Image {

    Class<? extends Payload> [] payload() default{};
    Class<?>[] groups() default {};
    String message() default "Only png,jpeg,jpg files are allowed";

    String[] types() default {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE};

    @Target({ ElementType.PARAMETER })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        Image[] value();
    }
}
