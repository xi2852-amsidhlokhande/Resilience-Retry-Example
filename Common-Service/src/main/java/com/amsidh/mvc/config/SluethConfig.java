package com.amsidh.mvc.config;


import org.springframework.beans.factory.BeanFactory;
import org.springframework.cloud.sleuth.instrument.async.TraceableExecutorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

@Configuration
public class SluethConfig {

	/*@Bean
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
*/
	@Bean
	public TraceableExecutorService executorService(BeanFactory beanFactory) {
		return new TraceableExecutorService(beanFactory, Executors.newCachedThreadPool());
	}
}
