package com.zl.webproject.model;


import java.util.Date;

/**
 * 更新(CAR_UPLOAD)
 * 
 * @author szc
 * @version 1.0.0 2017-08-06
 */
public class CarUploadEntity implements java.io.Serializable {
	
	/** 版本号 */
	private static final long serialVersionUID = 2390998271609184898L;
	
    
    /** 主键自增ID */
    private Integer id;
    
    /** 版本号 */
    private String uploadCode;
    
    /** 版本名称 */
    private String uploadName;
    
    /** 更新内容 */
    private String uploadContext;
    
    /** 更新时间 */
    private String uploadDate;
    
    /** 更新地址 */
    private String uploadUrl;
    
    
    /** 更新时间 */
    private String uploadDateStr;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUploadCode() {
		return uploadCode;
	}

	public void setUploadCode(String uploadCode) {
		this.uploadCode = uploadCode;
	}

	public String getUploadName() {
		return uploadName;
	}

	public void setUploadName(String uploadName) {
		this.uploadName = uploadName;
	}

	public String getUploadContext() {
		return uploadContext;
	}

	public void setUploadContext(String uploadContext) {
		this.uploadContext = uploadContext;
	}

	public String getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getUploadUrl() {
		return uploadUrl;
	}

	public void setUploadUrl(String uploadUrl) {
		this.uploadUrl = uploadUrl;
	}

	public String getUploadDateStr() {
		return uploadDateStr;
	}

	public void setUploadDateStr(String uploadDateStr) {
		this.uploadDateStr = uploadDateStr;
	}
}