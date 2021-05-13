package com.example.demo.controller;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.login.UserDto;

@RestController
public class RestHomeController {
	
	private static final Logger logger = LogManager.getLogger(RestHomeController.class);
	
	@RequestMapping(value="/api/hello")
    public ResponseEntity<UserDto> test(HttpServletResponse resp, Principal principal) throws IOException {
		
		UserDto userDto = new UserDto();
		
		if (!principal.getName().isEmpty()) {
			OAuth2User user = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getDetails();
			
			userDto.setUserName(user.getAttribute("nickname").toString());
			userDto.setUserId(user.getAttribute("sub").toString());
			userDto.setUserEmail(user.getAttribute("email").toString());
			
			if (logger.isDebugEnabled()) {
				logger.debug(user);
			}
			
		} else {
			userDto.setUserName("Anonymous");
			userDto.setUserId("Anonymous");
			userDto.setUserEmail("no-email@foo.com");
		}
				
		return ResponseEntity.ok(userDto);
    }
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value="/api/admin/hello")
    public ResponseEntity<String> testAdmin(HttpServletResponse resp, Principal principal) throws IOException {
		
		String nickname = "Annonymous";
		
		if (!principal.getName().isEmpty()) {
			OAuth2User user = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getDetails();
			nickname = user.getAttribute("nickname").toString();
			
			
			if (logger.isDebugEnabled()) {
				logger.debug(nickname);
				user.getAuthorities().forEach(auth -> logger.debug(auth));
			}		
		}				
		
		return ResponseEntity.ok(nickname + "(ADMIN)");
    }

	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> exception(Exception e) {		
		return new ResponseEntity<String>("/api/hello/ Failed!", HttpStatus.OK);
	}

}
