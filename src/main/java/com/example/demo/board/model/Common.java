package com.example.demo.board.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class Common implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="INS_DT", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date insDt;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPD_DT", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	private Date updDt;

	public Date getInsDt() {
		return insDt;
	}

	public void setInsDt(Date insDt) {
		this.insDt = insDt;
	}

	public Date getUpdDt() {
		return updDt;
	}

	public void setUpdDt(Date updDt) {
		this.updDt = updDt;
	}
	
	
	@PrePersist
    public void onInsert() {
		
		Date dt = new Date();
		insDt = dt;
		updDt = dt;
    }
	
	@PreUpdate
	public void onUpdate() {
		Date dt = new Date();
		updDt = dt;
	}
	
	

}
