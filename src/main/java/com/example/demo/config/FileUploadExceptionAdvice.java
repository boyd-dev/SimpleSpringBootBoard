package com.example.demo.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.board.model.UploadResponse;

// TODO
// 파일 크기는 서버측에서 검사하기로 한다.
@ControllerAdvice
public class FileUploadExceptionAdvice {
	
	private static final Logger logger = LogManager.getLogger(FileUploadExceptionAdvice.class);
	
	@ExceptionHandler(FileSizeLimitExceededException.class)
	@ResponseBody
    public UploadResponse handleFileSizeLimitException(FileSizeLimitExceededException exc, HttpServletRequest req, HttpServletResponse res) {
 
		if (logger.isDebugEnabled()) {
			logger.debug(exc.getMessage());
		}
		
		UploadResponse result = new UploadResponse();
		Map<String, String> m = new HashMap<>();
		m.put("message", "FileSizeLimitExceededException: " + exc.getMessage());
		result.setError(m);
        return result;
    }

}
