package com.amsidh.mvc.controller;

import com.amsidh.mvc.exception.ErrorMessage;
import com.amsidh.mvc.exception.Service3NotFound;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class Service3AdviceController {

    @ExceptionHandler(value = {Service3NotFound.class})
    public ErrorMessage handleService3NotFoundException(Service3NotFound service3NotFound, WebRequest webRequest){
       return ErrorMessage.builder()
                .message(service3NotFound.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .timestamp(new Date())
                .description(webRequest.getDescription(true))
                .build();
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ErrorMessage handleRuntimeException(RuntimeException exception, WebRequest webRequest){
        return ErrorMessage.builder()
                .message(exception.getMessage())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(new Date())
                .description(webRequest.getDescription(true))
                .build();
    }

}
