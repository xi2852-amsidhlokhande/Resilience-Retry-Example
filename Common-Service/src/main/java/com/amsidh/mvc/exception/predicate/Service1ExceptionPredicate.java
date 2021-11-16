package com.amsidh.mvc.exception.predicate;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Predicate;

@Slf4j
public class Service1ExceptionPredicate implements Predicate<Throwable> {
    @Override
    public boolean test(Throwable throwable) {
    /*	log.debug("Inside test method of HttpServerErrorPredicate");
        if (throwable instanceof HttpServerErrorException) {
            HttpServerErrorException httpServerErrorException = (HttpServerErrorException) throwable;
            if (httpServerErrorException.getStatusCode().is4xxClientError()) {
                return true;
            }
        }else if(throwable instanceof ResourceAccessException){
            return true;
        }
        return false;*/
        return true;
    }
}
