package com.amsidh.mvc.predicates;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import java.util.function.Predicate;

@Slf4j
public class Service2APIResponsePredicate  implements Predicate<ResponseEntity<String>> {
    @Override
    public boolean test(ResponseEntity<String> stringResponseEntity) {
        log.info("Response received from API {}",stringResponseEntity.getBody());
        return stringResponseEntity.getBody().contains("INPROGRESS");
    }
}
