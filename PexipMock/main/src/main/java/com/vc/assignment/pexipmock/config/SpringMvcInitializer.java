package com.vc.assignment.pexipmock.config;

import java.nio.charset.StandardCharsets;

import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpringMvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[]{AppConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[0];
	}

	@Override
	protected String[] getServletMappings() {
		return new String[]{"/"};
	}

	@Override
	protected Filter[] getServletFilters() {

		// encFilter must be the first filter otherwise it doesn't work
		return new Filter[]{new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true),
							new HiddenHttpMethodFilter()};
	}
}
