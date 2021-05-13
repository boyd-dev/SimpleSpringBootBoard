package com.example.demo.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 개발 중에 사용할 dummy 필터
 * @author boyd-dev
 *
 */
public class JwtDummyFilter extends OncePerRequestFilter {

	private static final Logger logger = LogManager.getLogger(JwtDummyFilter.class);
	
	private RequestMatcher requestMatcher = new AntPathRequestMatcher("/api/**");
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		if (requestMatcher.matches(request)) {
			
			if (logger.isDebugEnabled()) {
				logger.debug("DUMMY USER ================================================");
			}
			
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
			
			//프론트엔드 설정과 맞출 필요가 있다!
			Map<String, Object> attributes = new HashMap<>();
			attributes.put("sub", "12345");
			attributes.put("nickname", "foo-dev");
			attributes.put("email", "foo-dev@nate.com");
			
			String userNameAttributeName = "sub";
			
			OAuth2User userDetails = new DefaultOAuth2User(authorities, attributes, userNameAttributeName);				
			OAuth2AuthenticationToken authentication = new OAuth2AuthenticationToken(userDetails, authorities, userNameAttributeName);
            
			authentication.setDetails(userDetails);
			SecurityContextHolder.getContext().setAuthentication(authentication); 
			
		}
		
		filterChain.doFilter(request, response);
		
	}

}
