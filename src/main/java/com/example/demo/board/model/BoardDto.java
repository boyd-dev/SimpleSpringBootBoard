package com.example.demo.board.model;

import java.io.Serializable;

public class BoardDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long cnttId;
	
	private String boardId;
	
	private String cnttTitle;
	
	private String cnttText;
	
	private String authorId;
	
	private String userName;

	public String getBoardId() {
		return boardId;
	}

	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}

	public String getCnttTitle() {
		return cnttTitle;
	}

	public void setCnttTitle(String cnttTitle) {
		this.cnttTitle = cnttTitle;
	}

	public String getCnttText() {
		return cnttText;
	}

	public void setCnttText(String cnttText) {
		this.cnttText = cnttText;
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	public Long getCnttId() {
		return cnttId;
	}

	public void setCnttId(Long cnttId) {
		this.cnttId = cnttId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	
	

}
