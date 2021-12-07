package com.amsidh.mvc.controller;

import com.amsidh.mvc.domain.ErrorMessage;
import com.amsidh.mvc.exception.Service2NotFound;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class Service2AdviceController {

    @ExceptionHandler(value = {Service2NotFound.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleService2NotFoundException(Service2NotFound service2NotFound, WebRequest webRequest) {
        return ErrorMessage.builder()
                .message(service2NotFound.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .timestamp(new Date())
                .description(webRequest.getDescription(true))
                .build();
    }

    @ExceptionHandler(value = {RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleRuntimeException(RuntimeException exception, WebRequest webRequest) {
        return ErrorMessage.builder()
                .message(exception.getMessage())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(new Date())
                .description(webRequest.getDescription(true))
                .build();
    }

}
