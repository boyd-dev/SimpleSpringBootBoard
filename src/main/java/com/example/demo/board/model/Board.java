package com.example.demo.board.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="T_BOARD")
public class Board extends Common implements Serializable {		
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	//자동증가 시퀀스
	//타입을 Long으로 맞추어야 한다.
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="CNTT_ID")
	private Long cnttId;
	
	@Column(name="BOARD_ID", columnDefinition="VARCHAR(3)", nullable=false)
	private String boardId;
	
	@Column(name="CNTT_TITLE", columnDefinition="VARCHAR(1000)", nullable=false)
	private String cnttTitle;
	
	@Column(name="CNTT_TEXT", columnDefinition="MEDIUMTEXT", nullable=false)
	private String cnttText;
	
	@Column(name="AUTHOR_ID", columnDefinition="VARCHAR(255)", nullable=false)
	private String authorId;
	
	@Column(name="USER_NAME", columnDefinition="VARCHAR(255)", nullable=false)
	private String userName;
	
	@Column(name="CNTT_HIT", columnDefinition="INT DEFAULT 0")
	private int cnttHit;
	
	
	@OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
	@JoinColumn(name="ATTACH_CNTT_ID")
	private List<File> files = new ArrayList<>();
		

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	public Long getCnttId() {
		return cnttId;
	}

	public void setCnttId(Long cnttId) {
		this.cnttId = cnttId;
	}

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

	public int getCnttHit() {
		return cnttHit;
	}

	public void setCnttHit(int cnttHit) {
		this.cnttHit = cnttHit;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

		
	

}
