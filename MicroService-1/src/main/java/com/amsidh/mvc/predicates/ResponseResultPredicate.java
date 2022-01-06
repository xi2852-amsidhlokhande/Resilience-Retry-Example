package com.amsidh.mvc.predicates;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import java.util.function.Predicate;

@Slf4j
public class ResponseResultPredicate implements Predicate<ResponseEntity<String>> {
    @Override
    public boolean test(ResponseEntity<String> response) {
        log.info("Response received from API {}",response.getBody());
        return response.getBody().contains("INPROGRESS");
    }
}
