package com.amsidh.mvc.filter;

import static org.springframework.cloud.sleuth.instrument.web.TraceWebServletAutoConfiguration.TRACING_FILTER_ORDER;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import brave.Span;
import brave.Tracer;
import brave.propagation.ExtraFieldPropagation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
@Order(TRACING_FILTER_ORDER + 1)
public class RequestIdFilter extends GenericFilterBean {

	private final Tracer tracer;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		Span currentSpan = this.tracer.currentSpan();
		if (currentSpan == null) {
			chain.doFilter(request, response);
			return;
		}
		((HttpServletResponse) response).addHeader("TraceId", currentSpan.context().traceIdString());
		((HttpServletResponse) response).addHeader("RequestId", ExtraFieldPropagation.get("RequestId"));
		chain.doFilter(request, response);
	}

}
