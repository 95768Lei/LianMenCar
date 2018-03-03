package com.zl.webproject.model;

import java.util.Date;

/**
 * 认证用户信息(CAR_INTERMEDIARY)
 * 
 * @author szc
 * @version 1.0.0 2017-08-06
 */
public class CarIntermediaryEntity implements java.io.Serializable {
    /** 版本号 */
	private static final long serialVersionUID = -7431431553369024453L;
    
    /** 主键自增ID */
    private Integer id;
    
    /** 用户ID*/
    private Integer inserUserId;
    
    /** 0：待审核，1：审核通过，2：审核不通过 */
    private Integer inserState;
    
    /** 身份证正面 */
    private String inserPositiveImg;
    
    /** 身份证反面 */
    private String inserOppositeImg;
    
    /** 手持身份证 */
    private String inserWholeImg;
    
    /** 提交时间 */
    private Date inserCreateDate;
    
    /** 审核时间 */
    private Date inserAuditDate;
    
    /** 民族 */
    private String inserNation;
    
    /** 地址 */
    private String inserAddress;
    
    /** 姓名 */
    private String inserName;

	/** 身份证号 */
	private String inserNo;

	/** 生日 */
	private String inserDate;
    
    private String inserAuditDateStr;
    
    private String inserCreateDateStr;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getInserUserId() {
		return inserUserId;
	}

	public void setInserUserId(Integer inserUserId) {
		this.inserUserId = inserUserId;
	}

	public Integer getInserState() {
		return inserState;
	}

	public void setInserState(Integer inserState) {
		this.inserState = inserState;
	}

	public String getInserPositiveImg() {
		return inserPositiveImg;
	}

	public void setInserPositiveImg(String inserPositiveImg) {
		this.inserPositiveImg = inserPositiveImg;
	}

	public String getInserOppositeImg() {
		return inserOppositeImg;
	}

	public void setInserOppositeImg(String inserOppositeImg) {
		this.inserOppositeImg = inserOppositeImg;
	}

	public String getInserWholeImg() {
		return inserWholeImg;
	}

	public void setInserWholeImg(String inserWholeImg) {
		this.inserWholeImg = inserWholeImg;
	}

	public Date getInserCreateDate() {
		return inserCreateDate;
	}

	public void setInserCreateDate(Date inserCreateDate) {
		this.inserCreateDate = inserCreateDate;
	}

	public Date getInserAuditDate() {
		return inserAuditDate;
	}

	public void setInserAuditDate(Date inserAuditDate) {
		this.inserAuditDate = inserAuditDate;
	}

	public String getInserNation() {
		return inserNation;
	}

	public void setInserNation(String inserNation) {
		this.inserNation = inserNation;
	}

	public String getInserAddress() {
		return inserAddress;
	}

	public void setInserAddress(String inserAddress) {
		this.inserAddress = inserAddress;
	}

	public String getInserName() {
		return inserName;
	}

	public void setInserName(String inserName) {
		this.inserName = inserName;
	}

	public String getInserAuditDateStr() {
		return inserAuditDateStr;
	}

	public void setInserAuditDateStr(String inserAuditDateStr) {
		this.inserAuditDateStr = inserAuditDateStr;
	}

	public String getInserCreateDateStr() {
		return inserCreateDateStr;
	}

	public void setInserCreateDateStr(String inserCreateDateStr) {
		this.inserCreateDateStr = inserCreateDateStr;
	}

	public String getInserNo() {
		return inserNo;
	}

	public void setInserNo(String inserNo) {
		this.inserNo = inserNo;
	}

	public String getInserDate() {
		return inserDate;
	}

	public void setInserDate(String inserDate) {
		this.inserDate = inserDate;
	}
}