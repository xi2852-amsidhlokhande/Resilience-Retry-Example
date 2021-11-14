package com.amsidh.mvc.controller;

import com.amsidh.mvc.exception.ErrorMessage;
import com.amsidh.mvc.exception.Service1NotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
@Slf4j
public class Service1AdviceController {

    @ExceptionHandler(value = {Service1NotFound.class})
    public ErrorMessage handleService1NotFoundException(Service1NotFound service1NotFound, WebRequest webRequest) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .message(service1NotFound.getMessage())
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
