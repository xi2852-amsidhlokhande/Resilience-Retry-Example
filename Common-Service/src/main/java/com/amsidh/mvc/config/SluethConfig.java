package com.amsidh.mvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import brave.baggage.BaggageField;
import brave.baggage.CorrelationScopeConfig.SingleCorrelationField;
import brave.context.slf4j.MDCScopeDecorator;
import brave.propagation.CurrentTraceContext.ScopeDecorator;

@Configuration
public class SluethConfig {

	@Bean
	BaggageField RequestIdField() {
		return BaggageField.create("RequestId");
	}

	@Bean
	ScopeDecorator mdcScopeDecorator() {
	    return MDCScopeDecorator.newBuilder()
	            .clear()
	            .add(SingleCorrelationField.newBuilder(RequestIdField())
	                    .flushOnUpdate()
	                    .build())
	            .build();
	}
}
