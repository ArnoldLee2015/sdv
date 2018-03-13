package com.lee.sdv.web.controller.dto;

import java.util.Date;
import java.util.List;

public class ExportDateRecordDto {
	/**
	 * 访视时间开始
	 */
	private Date startDate;
	/**
	 * 访视时间结束
	 */
	private Date endDate;
	/**
	 * 导出患者列表
	 */
	private List<Long> patientIds;
	/**
	 * 接收邮箱
	 */
	private String email;

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<Long> getPatientIds() {
		return patientIds;
	}

	public void setPatientIds(List<Long> patientIds) {
		this.patientIds = patientIds;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
