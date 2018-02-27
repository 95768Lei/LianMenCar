package com.zl.webproject.model;

import java.util.Date;

/**
 * 信息表(CAR_MESSAGE)
 * 
 * @author szc
 * @version 1.0.0 2017-08-06
 */
public class CarMessageEntity implements java.io.Serializable {
	
	/** 版本号 */
	private static final long serialVersionUID = 2193213325676153453L;

	/** 自增主键ID */
	private Integer id;

	/** 关联用户信息 */
	private Integer messUserId;

	/** 信息内容 */
	private String messContext;

	/** 发送时间 */
	private String messDate;

	private String messDateStr;
	
	/** 是否发送短信 */
	private Integer messSMS;
	
	/** 未读标识 0:已读，1：未读 */
	private Integer messUnread;
	
	/** 信息类型 */
	private Integer messType;
	
	/** 信息表头*/
	private String messTitle;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMessUserId() {
		return messUserId;
	}

	public void setMessUserId(Integer messUserId) {
		this.messUserId = messUserId;
	}

	public String getMessContext() {
		return messContext;
	}

	public void setMessContext(String messContext) {
		this.messContext = messContext;
	}

	public String getMessDate() {
		return messDate;
	}

	public void setMessDate(String messDate) {
		this.messDate = messDate;
	}

	public String getMessDateStr() {
		return messDateStr;
	}

	public void setMessDateStr(String messDateStr) {
		this.messDateStr = messDateStr;
	}
	
	public Integer getMessSMS() {
		return messSMS;
	}
	
	public void setMessSMS(Integer messSMS) {
		this.messSMS = messSMS;
	}

	public Integer getMessUnread() {
		return messUnread;
	}

	public void setMessUnread(Integer messUnread) {
		this.messUnread = messUnread;
	}

	public Integer getMessType() {
		return messType;
	}

	public void setMessType(Integer messType) {
		this.messType = messType;
	}

	public String getMessTitle() {
		return messTitle;
	}

	public void setMessTitle(String messTitle) {
		this.messTitle = messTitle;
	}
}