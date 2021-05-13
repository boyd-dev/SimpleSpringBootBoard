package com.example.demo.utils;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUtils {

	private static final Logger logger = LogManager.getLogger(FileUtils.class);
	

	@Autowired
	Environment env;
	
	
	// 단일 이미지 파일 업로드
	public String parseCkEditorImgPath(MultipartFile file) throws Exception {

		String storePathString = "";
		String filePath = "";
		String result = env.getProperty("images.hostUrl");

		try {
			
			//일자별로 폴더를 만들어서 넣는다.
			DateFormat fmt = new SimpleDateFormat("yyyyMMdd");
			String strDate = fmt.format(new Date());
			
			//디스크의 물리적인 경로
			storePathString = env.getProperty("images.path") + File.separator + strDate + File.separator;

			File saveFolder = new File(storePathString);

			if (!saveFolder.exists() || saveFolder.isFile()) {
				saveFolder.mkdirs();
			}
			
			//TODO 한글 파일명이 안되므로 파일명을 아스키 문자열로 변환해주는 작업이 필요하다! 
			//
			//
		
			filePath = storePathString + file.getOriginalFilename();
			
			if (logger.isDebugEnabled()) {
				logger.debug(filePath + " " + file.getSize());
			}
			
			file.transferTo(new File(filePath));
			result = result + strDate + File.separator + file.getOriginalFilename();
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		return result;
	}


	public void deleteFile(String fileStreCours, String streFileNm) throws Exception {

		String path = fileStreCours + streFileNm;
		try {

			File f = new File(path);
			if (f.isFile()) {
				f.delete();
			}

		} catch (Exception e) {
		    e.printStackTrace();
		}
	}


}