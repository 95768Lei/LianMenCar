package com.zl.webproject.model;

import java.util.Date;

/**
 * 服务信息(CAR_SERVER)
 * 
 * @author szc
 * @version 1.0.0 2017-08-06
 */
public class CarServerEntity implements java.io.Serializable {
	/** 版本号 */
	private static final long serialVersionUID = 8016403878384028617L;

	/** 自增主键ID */
	private Integer id;
	
	/** 置顶 */
	private Integer serHotTop;
	
	/** 服务类型。1：保险，2：维修，3：年审 */ 
	private Integer serType;

	/** 服务标题 */
	private String serTitle;

	/** 服务内容 */
	private String serContext;

	/** 服务时间 */
	private Date serDate;
	
	/** 联系人 */
	private Integer serUserId;

	/** 城市 */
	private Integer serCityId;
	
	/** 地址 */
	private String serAddress;
	
	/** 宣传图片 */
	private String serImg;
	
	/** 刷新时间 */
	private Date serRefreshDate;
	
	/** 时间 */
	private String serDateStr;
	
	/** 刷新时间 */
	private String serRefreshDateStr;
	
	 /** 标签-实体Bean */
    private CarUserEntity carUser;
    
    /** 标签-实体Bean */
    private CarAreaCitysEntity city;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSerHotTop() {
		return serHotTop;
	}

	public void setSerHotTop(Integer serHotTop) {
		this.serHotTop = serHotTop;
	}

	public Integer getSerType() {
		return serType;
	}

	public void setSerType(Integer serType) {
		this.serType = serType;
	}

	public String getSerTitle() {
		return serTitle;
	}

	public void setSerTitle(String serTitle) {
		this.serTitle = serTitle;
	}

	public String getSerContext() {
		return serContext;
	}

	public void setSerContext(String serContext) {
		this.serContext = serContext;
	}

	public Date getSerDate() {
		return serDate;
	}

	public void setSerDate(Date serDate) {
		this.serDate = serDate;
	}

	public Integer getSerUserId() {
		return serUserId;
	}

	public void setSerUserId(Integer serUserId) {
		this.serUserId = serUserId;
	}

	public Integer getSerCityId() {
		return serCityId;
	}

	public void setSerCityId(Integer serCityId) {
		this.serCityId = serCityId;
	}

	public String getSerAddress() {
		return serAddress;
	}

	public void setSerAddress(String serAddress) {
		this.serAddress = serAddress;
	}

	public String getSerImg() {
		return serImg;
	}

	public void setSerImg(String serImg) {
		this.serImg = serImg;
	}

	public Date getSerRefreshDate() {
		return serRefreshDate;
	}

	public void setSerRefreshDate(Date serRefreshDate) {
		this.serRefreshDate = serRefreshDate;
	}

	public String getSerDateStr() {
		return serDateStr;
	}

	public void setSerDateStr(String serDateStr) {
		this.serDateStr = serDateStr;
	}

	public String getSerRefreshDateStr() {
		return serRefreshDateStr;
	}

	public void setSerRefreshDateStr(String serRefreshDateStr) {
		this.serRefreshDateStr = serRefreshDateStr;
	}

	public CarUserEntity getCarUser() {
		return carUser;
	}

	public void setCarUser(CarUserEntity carUser) {
		this.carUser = carUser;
	}

	public CarAreaCitysEntity getCity() {
		return city;
	}

	public void setCity(CarAreaCitysEntity city) {
		this.city = city;
	}
}