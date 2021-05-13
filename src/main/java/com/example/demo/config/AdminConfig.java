package com.example.demo.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 설정 파일에서 리스트형 키값을 리스트로 리턴한다. 
 *
 */

@Component
@EnableConfigurationProperties
@ConfigurationProperties
public class AdminConfig {
	
	private List<String> admins = new ArrayList<>();
	
	public List<String> getAdmins(){
	      return this.admins;
	}

}
