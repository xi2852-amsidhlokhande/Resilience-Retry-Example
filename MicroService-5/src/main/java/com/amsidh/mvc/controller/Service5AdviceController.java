package com.amsidh.mvc.controller;

import com.amsidh.mvc.domain.ErrorMessage;
import com.amsidh.mvc.exception.Service5NotFound;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class Service5AdviceController {

    @ExceptionHandler(value = {Service5NotFound.class})
    public ErrorMessage handleService5NotFoundException(Service5NotFound service5NotFound, WebRequest webRequest) {
        return ErrorMessage.builder()
                .message(service5NotFound.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .timestamp(new Date())
                .description(webRequest.getDescription(true))
                .build();
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ErrorMessage handleRuntimeException(RuntimeException exception, WebRequest webRequest) {
        return ErrorMessage.builder()
                .message(exception.getMessage())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(new Date())
                .description(webRequest.getDescription(true))
                .build();
    }

}
