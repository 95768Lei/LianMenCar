package com.zl.webproject.model;


import java.util.Date;

/**
 * 广告(CAR_ADVERT)
 * 
 * @author szc
 * @version 1.0.0 2017-08-06
 */

public class CarAdvertEntity implements java.io.Serializable {
	
	/** 版本号 */
	private static final long serialVersionUID = 2390998271609184898L;
    
    /** 主键自增ID */

    private Integer id;
    
    /** 展示图片路径 */
    private String advertImg;
    
    /** 图片路径 */
    private String advertHtml;
    
    /** 下载地址 */
    private String advertDown;
    
    /** 绑定用户 */
    private String advertUserName;
    
    /** 绑定用户电话 */
    private String advertUserPhone;
    
    /** 点击率 */
    private Integer advertCount;
    
    /** 开始时间 */
    private String advertStartDate;
    
    /** 结束时间 */
    private String advertEndDate;
    
    /** 周期数（月/数） */
    private Integer advertMethodNum;
    
    /** 广告名称 */
    private String advertName;
    
    /** 开始时间 */
    private String advertStartDateStr;
    
    /** 结束时间 */
    private String advertEndDateStr;
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAdvertImg() {
		return advertImg;
	}

	public void setAdvertImg(String advertImg) {
		this.advertImg = advertImg;
	}

	public String getAdvertHtml() {
		return advertHtml;
	}

	public void setAdvertHtml(String advertHtml) {
		this.advertHtml = advertHtml;
	}

	public String getAdvertDown() {
		return advertDown;
	}

	public void setAdvertDown(String advertDown) {
		this.advertDown = advertDown;
	}

	public String getAdvertUserName() {
		return advertUserName;
	}

	public void setAdvertUserName(String advertUserName) {
		this.advertUserName = advertUserName;
	}

	public String getAdvertUserPhone() {
		return advertUserPhone;
	}

	public void setAdvertUserPhone(String advertUserPhone) {
		this.advertUserPhone = advertUserPhone;
	}

	public Integer getAdvertCount() {
		return advertCount;
	}

	public void setAdvertCount(Integer advertCount) {
		this.advertCount = advertCount;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getAdvertStartDate() {
		return advertStartDate;
	}

	public void setAdvertStartDate(String advertStartDate) {
		this.advertStartDate = advertStartDate;
	}

	public String getAdvertEndDate() {
		return advertEndDate;
	}

	public void setAdvertEndDate(String advertEndDate) {
		this.advertEndDate = advertEndDate;
	}

	public Integer getAdvertMethodNum() {
		return advertMethodNum;
	}

	public void setAdvertMethodNum(Integer advertMethodNum) {
		this.advertMethodNum = advertMethodNum;
	}
	
	public String getAdvertName() {
		return advertName;
	}

	public void setAdvertName(String advertName) {
		this.advertName = advertName;
	}

	public String getAdvertStartDateStr() {
		return advertStartDateStr;
	}

	public void setAdvertStartDateStr(String advertStartDateStr) {
		this.advertStartDateStr = advertStartDateStr;
	}

	public String getAdvertEndDateStr() {
		return advertEndDateStr;
	}

	public void setAdvertEndDateStr(String advertEndDateStr) {
		this.advertEndDateStr = advertEndDateStr;
	}
}