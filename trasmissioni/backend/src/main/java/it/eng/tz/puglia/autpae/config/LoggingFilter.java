package it.eng.tz.puglia.autpae.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

public class LoggingFilter extends OncePerRequestFilter {

	private final static Logger LOGGER = LoggerFactory.getLogger(LoggingFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		MDC.put("requestId", UUID.randomUUID().toString());
		logRequestInfo(request);
		filterChain.doFilter(request, response);
	}

	private void logRequestInfo(HttpServletRequest request) {

		StringBuilder parameterMap = new StringBuilder();
		if (request.getParameterMap() != null) {
			for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
				parameterMap.append(entry.getKey().concat(":").concat(Arrays.toString(entry.getValue())));
			}
		}

		String authorization = request.getHeader("Authorization");

		LOGGER.info("RequestURI: {} Method: {} ParameterMap: {} Authorization: {}", request.getRequestURI(),
				request.getMethod(), parameterMap.toString(), authorization);
	}
}