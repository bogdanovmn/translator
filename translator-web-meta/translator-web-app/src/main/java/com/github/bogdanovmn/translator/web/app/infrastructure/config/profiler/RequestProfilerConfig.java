package com.github.bogdanovmn.translator.web.app.infrastructure.config.profiler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RequestProfilerConfig implements WebMvcConfigurer {

	@Autowired
	RequestStatisticsInterceptor requestStatisticsInterceptor;

	@Bean
	public RequestStatisticsInterceptor requestStatisticsInterceptor() {
		return new RequestStatisticsInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(requestStatisticsInterceptor)
			.addPathPatterns("/**")
			.excludePathPatterns("/webjars/**")
			.excludePathPatterns("/js/**")
			.excludePathPatterns("/img/**")
			.excludePathPatterns("/css/**");
	}
}