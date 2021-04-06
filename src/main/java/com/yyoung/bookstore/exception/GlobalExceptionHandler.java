package com.yyoung.bookstore.exception;

import com.yyoung.bookstore.utils.controller.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
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
    public ErrorResponse missingRequestParameterExceptionHandler(MissingServletRequestParameterException exception) {
        log.warn(exception.toString());
        return new ErrorResponse(exception.getParameterName() + "参数不能为空");
    }

    // Missing request body
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResponse missingRequestBodyExceptionHandler(HttpMessageNotReadableException exception) {
        return new ErrorResponse("请求体不能为空");
    }

    // Invalid argument
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse invalidArgumentExceptionHandler(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            if (!errors.isEmpty()) {
                String message = errors.get(0).getDefaultMessage();
                return new ErrorResponse(message);
            }
        }
        return new ErrorResponse("参数不合法");
    }

    // Invalid path variable
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponse constraintViolationExceptionHandler(ConstraintViolationException exception) {
        String message = exception.getMessage();
        if (message.isEmpty()) {
            return new ErrorResponse("参数不合法");
        }
        return new ErrorResponse(message);
    }

    // Method not allowed
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ErrorResponse httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException exception) {
        return new ErrorResponse("不支持的请求方法: " + exception.getMethod(), HttpStatus.METHOD_NOT_ALLOWED);
    }
}
