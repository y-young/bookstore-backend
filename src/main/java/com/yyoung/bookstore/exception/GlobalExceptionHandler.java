package com.yyoung.bookstore.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Missing request parameter
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String missingRequestParameterExceptionHandler(MissingServletRequestParameterException exception) {
        log.warn(exception.toString());
        return exception.getParameterName() + "参数不能为空";
    }

    // Missing request body
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public String missingRequestBodyExceptionHandler(HttpMessageNotReadableException exception) {
        return "请求体不能为空";
    }

    // Invalid argument
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String invalidArgumentExceptionHandler(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            if (!errors.isEmpty()) {
                return errors.get(0).getDefaultMessage();
            }
        }
        return "参数不合法";
    }

    // Invalid path variable
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public String constraintViolationExceptionHandler(ConstraintViolationException exception) {
        String message = exception.getMessage();
        if (message.isEmpty()) {
            return "参数不合法";
        }
        return message;
    }
}
