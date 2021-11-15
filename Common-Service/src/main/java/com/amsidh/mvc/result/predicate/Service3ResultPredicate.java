package com.amsidh.mvc.result.predicate;

import java.util.function.Predicate;

import com.amsidh.mvc.domain.ErrorMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Service3ResultPredicate implements Predicate<ErrorMessage> {

	@Override
	public boolean test(ErrorMessage errorMessage) {
		log.info(errorMessage.toString());
		return true;
	}

}
