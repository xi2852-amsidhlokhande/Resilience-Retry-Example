package com.amsidh.mvc.controller;

import com.amsidh.mvc.domain.ErrorMessage;
import com.amsidh.mvc.exception.Service3NotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
@Slf4j
public class Service3AdviceController {

    @ExceptionHandler(value = {Service3NotFound.class})
    public ErrorMessage handleService3NotFoundException(Service3NotFound service3NotFound, WebRequest webRequest) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .message(service3NotFound.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .timestamp(new Date())
                .description(webRequest.getDescription(true))
                .build();
        return errorMessage;
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ErrorMessage handleRuntimeException(RuntimeException exception, WebRequest webRequest) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .message(exception.getMessage())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(new Date())
                .description(webRequest.getDescription(true))
                .build();
        return errorMessage;
    }

}
