package com.example.demo.board.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 외부 디렉토리에 저장된 이미지를 HTTP응답으로 전환
 *
 */
public class ImageServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		String filename = req.getPathInfo().substring(1);
        File file = new File(getInitParameter("images.path"), filename);
        
        if (file.exists()) {
        	 res.setHeader("Content-Type", getServletContext().getMimeType(filename));
             res.setHeader("Content-Length", String.valueOf(file.length()));
             res.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
             Files.copy(file.toPath(), res.getOutputStream());
        } else {
        	// 이미지가 없는 경우 표시할 이미지
        	// 수동으로 복사해 넣는다.
        	res.sendRedirect("/static/media/no-image.PNG");
        }       
		
	}

}
