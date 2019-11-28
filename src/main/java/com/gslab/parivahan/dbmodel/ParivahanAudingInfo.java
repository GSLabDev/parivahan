package com.gslab.parivahan.dbmodel;

import java.util.Date;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class ParivahanAudingInfo {

	protected Date createdDate;
	protected Date updatedDate;
	
	public ParivahanAudingInfo() {
		// TODO Auto-generated constructor stub
	}
	public ParivahanAudingInfo(Date createdDate, Date updatedDate) {
		super();
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
}
