package ru.practicum.shareit.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.warn("Validation error", ex);

        ex.getBindingResult().getAllErrors().forEach(er ->
                log.warn("\"" + ((FieldError) er).getField() + "\" " + er.getDefaultMessage()));

        HttpStatus status = HttpStatus.BAD_REQUEST;

        return ResponseEntity
                .status(status)
                .body(ErrorResponse.builder()
                        .status(status.value())
                        .error("Validation error")
                        .build());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleModelNotFoundException(ModelNotFoundException ex) {
        log.warn(ex.getMessage());

        HttpStatus status = HttpStatus.NOT_FOUND;

        return ResponseEntity
                .status(status)
                .body(ErrorResponse.builder()
                        .status(status.value())
                        .error(ex.getMessage())
                        .build());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Throwable ex) {
        log.warn(ex.getMessage(), ex);

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity
                .status(httpStatus)
                .body(ErrorResponse.builder()
                        .status(httpStatus.value())
                        .error(httpStatus.getReasonPhrase())
                        .build());
    }
}
