package com.amsidh.mvc.exception.predicate;

import java.util.function.Predicate;

import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Service5ExceptionPredicate implements Predicate<Throwable> {
    @Override
    public boolean test(Throwable throwable) {
    	/*log.debug("Inside test method of HttpServerErrorPredicate");
        if (throwable instanceof HttpServerErrorException) {
            HttpServerErrorException httpServerErrorException = (HttpServerErrorException) throwable;
            if (httpServerErrorException.getStatusCode().is4xxClientError()) {
                return true;
            }
        }else if(throwable instanceof ResourceAccessException){
            ResourceAccessException resourceAccessException = (ResourceAccessException) throwable;
            return true;
        }
        return false;*/
        return true;
    }
}
