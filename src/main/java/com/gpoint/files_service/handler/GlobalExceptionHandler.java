package com.gpoint.files_service.handler;
import com.gpoint.files_service.exception.EntityNotFoundException;
import com.gpoint.files_service.exception.S3Exception;
import com.gpoint.files_service.exception.ValidationException;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
@Hidden
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, List<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return e.getBindingResult().getAllErrors().stream()
                .collect(Collectors.groupingBy(error -> ((FieldError) error).getField(),
                        Collectors.mapping(error -> Objects.requireNonNullElse(error.getDefaultMessage(), ""),
                                Collectors.toList()))
                );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(EntityNotFoundException e, HttpServletRequest request) {
        log.error("Not found: {}", e.getMessage());
        return buildErrorResponse(e, request);
    }

    @ExceptionHandler(S3Exception.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleS3Exception(S3Exception e, HttpServletRequest request) {
        log.error("S3 Exception: {}", e.getMessage());
        return buildErrorResponse(e, request);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        log.error("Runtime exception: {}", e.getMessage(), e);
        return buildErrorResponse(e, request);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDataValidationException(ValidationException e, HttpServletRequest request) {
        log.error("Data validation error: {}", e.getMessage());
        return buildErrorResponse(e, request);
    }

    private ErrorResponse buildErrorResponse(Exception e, HttpServletRequest request) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .url(request.getRequestURI())
                .message(e.getMessage())
                .build();
    }
}
