package io.aval;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class WebConfig  extends WebMvcConfigurerAdapter{
	
	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	    registry.addResourceHandler("/uploads/**").addResourceLocations("file:/private/tmp/uploads/");
	    
	}
	
//	
//	@Override
//	public void addViewControllers(ViewControllerRegistry registry) {
//		
//		registry.addViewController("/").setViewName("forward:/index.jsp");
////		registry.addViewController("/media-catalog/api/users").setViewName("forward:/users");
//		
//	}
	
}
