package com.amsidh.mvc.config;

import brave.baggage.BaggageField;
import brave.baggage.CorrelationScopeConfig.SingleCorrelationField;
import brave.context.slf4j.MDCScopeDecorator;
import brave.propagation.CurrentTraceContext.ScopeDecorator;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.cloud.sleuth.instrument.async.TraceableExecutorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import brave.baggage.BaggageField;
import brave.baggage.CorrelationScopeConfig.SingleCorrelationField;
import brave.context.slf4j.MDCScopeDecorator;
import brave.propagation.CurrentTraceContext.ScopeDecorator;
import java.util.concurrent.Executors;

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

	@Bean
	public TraceableExecutorService executorService(BeanFactory beanFactory) {
		return new TraceableExecutorService(beanFactory, Executors.newCachedThreadPool());
	}
}
