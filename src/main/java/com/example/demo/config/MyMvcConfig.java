package com.example.demo.config;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.example.demo.board.controller.ImageServlet;

@Configuration
@EnableWebMvc
public class MyMvcConfig implements WebMvcConfigurer {
	
	private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {"classpath:/static/", "classpath:/public/"};

	@Resource
	private Environment env;
	
	// 정적 리소스는 보통 제한없이(로그인이 필요없는) 서비스되도록 설정한다.
	// 정적 리소스의 디폴트 루트는 /resources/static 
	// 예를 들어 /resources/static/main.html은 http://localhost:8080/main.html으로 볼 수 있다.
	// 시큐리티가 적용되면 필터에 의해 모두 차단되므로 필터 예외 설정한다.
	// .antMatchers("/static/**").permitAll()

	// 요청된 URL 경로와 물리적인 저장 경로를 매핑하는 역할
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {	
		registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);	
	}
	
		
	// 컨트롤러 작성 없이 특정 뷰로 이동하기
	// 여기서는 /app/** 패턴은 다시 SPA(react-router)가 처리하도록 포워딩한다.
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
	    registry.addViewController("/app/**").setViewName("forward:/index.html");	    
	}	
	
	// 원래는 JSP와 같은 뷰 처리를 지원하기 위해 설정하지만 스프링 부트에서는 JSP를 권장하지 않는다.
	// 스프링 부트에서 JSP를 쓰지 않는한 별도로 설정할 이유는 없다.
	@Bean
	public InternalResourceViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setViewNames(new String[] {"*.htm", "*.html"}); //뷰 페이지의 패턴을 제한
		return resolver;
	}
	  
	
	// 스프링 부트에서 서블릿 등록
	// 이미지 게시판의 이미지 URL에 대하여 응답생성
	// 정적 리소스의 경로에 업로드되면 업로드 후에 reload가 되지 않아서 이미지 경로가 리턴되지 않는 문제가 있으므로
	// 서블릿으로 이미지 경로를 응답하도록 한다.
	@Bean
	public ServletRegistrationBean<ImageServlet> servletRegistrationBean(){
		
		ServletRegistrationBean<ImageServlet> bean = new ServletRegistrationBean<>(new ImageServlet());
		bean.addUrlMappings("/uploadimages/*");		
		Map<String, String> initParams = new HashMap<>();
		initParams.put("images.path", env.getProperty("images.path"));
		bean.setInitParameters(initParams);
		return bean;
	}
	
	
}
