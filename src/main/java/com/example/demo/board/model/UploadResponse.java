package com.example.demo.board.model;

import java.io.Serializable;
import java.util.Map;

// CKEditor 이미지 업로드 응답용 클래스
public class UploadResponse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String url;
	
	private Map<String, String> error;
	
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Map<String, String> getError() {
		return error;
	}
	public void setError(Map<String, String> error) {
		this.error = error;
	}

}
