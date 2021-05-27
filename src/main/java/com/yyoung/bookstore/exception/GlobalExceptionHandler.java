package com.yyoung.bookstore.exception;

import com.yyoung.bookstore.dto.api.ErrorResponse;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Missing request parameter
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ErrorResponse missingRequestParameterExceptionHandler(MissingServletRequestParameterException exception) {
        return new ErrorResponse(exception.getParameterName() + "参数不能为空");
    }

    // Missing request body
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResponse missingRequestBodyExceptionHandler() {
        return new ErrorResponse("参数不合法或请求体为空");
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

    // Invalid path variable or request param
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponse constraintViolationExceptionHandler(ConstraintViolationException exception) {
        String message = "";
        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            message = violation.getMessage();
            break;
        }
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

    // Bad credentials
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadCredentialsException.class)
    public ErrorResponse badCredentialsExceptionHandler(BadCredentialsException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    // Invalid path variable type
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public void methodArgumentTypeMismatchExceptionHandler() {
    }

    // File size limit exceeded
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FileSizeLimitExceededException.class)
    public ErrorResponse fileSizeLimitExceededExceptionHandler(FileSizeLimitExceededException exception) {
        return new ErrorResponse("上传的文件大小超过限制");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ErrorResponse validationExceptionHandler(ValidationException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MultipartException.class)
    public ErrorResponse multipartExceptionHandler(MultipartException exception) {
        return new ErrorResponse("文件上传失败");
    }
}
