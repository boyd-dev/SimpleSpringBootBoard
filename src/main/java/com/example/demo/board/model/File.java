package com.example.demo.board.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

// 사용하지는 않는다.
@Entity
@Table(name="T_FILES")
public class File extends Common implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="FILE_ID")
	private Long fileId;
	
	
	@Column(name="ORIGINAL_FILE_NM", columnDefinition="VARCHAR(255)", nullable=false)
	private String originalFileName;
	
	@Column(name="STORED_FILE_NM", columnDefinition="VARCHAR(255)", nullable=false)
	private String storedFileName;
	
	@Column(name="FILE_PATH", columnDefinition="VARCHAR(2000)", nullable=false)
	private String filePath;
	
	@Column(name="FILE_EXT", columnDefinition="VARCHAR(20)")
	private String fileExt;
	
	@Column(name="FILE_SIZE", columnDefinition="DECIMAL(8,0)", nullable=false)
	private int fileSize;

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public String getStoredFileName() {
		return storedFileName;
	}

	public void setStoredFileName(String storedFileName) {
		this.storedFileName = storedFileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	public int getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}	
	
	
	

}
