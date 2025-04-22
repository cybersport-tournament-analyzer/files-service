package com.gpoint.files_service.annotation.image;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class ImageValidator implements ConstraintValidator<Image, MultipartFile> {

    private List<String> types;

    @Override
    public void initialize(Image image) {
        log.info("File validator initialized!!");
        types = Arrays.stream(image.types()).toList();
    }

    @Override
    public boolean isValid(MultipartFile multipartFile,
                           ConstraintValidatorContext constraintValidatorContext) {

        log.info("Validating file");
        String contentType = multipartFile.getContentType();
        assert contentType != null;

        return types.contains(contentType);
    }
}