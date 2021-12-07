package com.amsidh.mvc.controller;

import com.amsidh.mvc.domain.ErrorMessage;
import com.amsidh.mvc.exception.Service4NotFound;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class Service4AdviceController {

    @ExceptionHandler(value = {Service4NotFound.class})
    public ErrorMessage handleService4NotFoundException(Service4NotFound service4NotFound, WebRequest webRequest) {
        return ErrorMessage.builder()
                .message(service4NotFound.getMessage())
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
