package it.eng.tz.puglia.autpae.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.scheduling.annotation.EnableScheduling;

import it.eng.tz.puglia.servizi_esterni.logging.LoggingInterceptor;

@Configuration
//@EnableScheduling
//@EnableAsync 			// non dovrebbe servire più
public class ApplicationConfiguration {
	
	@Bean
	public LoggingFilter loggingFilter() {
		return new LoggingFilter();		
	}
	
	@Bean
	public LoggingInterceptor loggingInterceptor() {
		return new LoggingInterceptor();
	}
	
	@Bean
	public FilterRegistrationBean<LoggingFilter> loggingFilterRegistration() {

		FilterRegistrationBean<LoggingFilter> filterRegistrationBean = new FilterRegistrationBean<LoggingFilter>();
		filterRegistrationBean.setFilter(loggingFilter());
		filterRegistrationBean.addUrlPatterns("/*");
		filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);

		return filterRegistrationBean;
	}
	
}