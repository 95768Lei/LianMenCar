package com.zl.webproject.model;

import java.util.Date;

/**
 * 便捷查询记录(CAR_QUERY)
 * 
 * @author szc
 * @version 1.0.0 2017-08-06
 */
public class CarQueryEntity implements java.io.Serializable {
	
	/** 版本号 */
	private static final long serialVersionUID = 2390998271609184898L;
	
    
    /** 主键自增ID */
    private Integer id;
    
    /** 查询用户  */
    private Integer queryUserId;
    
    /** 查询种类1：获取违章信息，2：4S保养记录查询，3：车架号VIN（车辆识别代码）信息查询 */
    private Integer queryType;
    
    /** 查询时间 */
    private Date queryDate;
    
    /** 金额 */
    private Integer queryPrice;
    
    /** 获得用户信息-实体Bean */
    private CarUserEntity carUserEntity;
    
    private String queryDateStr;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getQueryUserId() {
		return queryUserId;
	}

	public void setQueryUserId(Integer queryUserId) {
		this.queryUserId = queryUserId;
	}

	public Integer getQueryType() {
		return queryType;
	}

	public void setQueryType(Integer queryType) {
		this.queryType = queryType;
	}

	public Date getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(Date queryDate) {
		this.queryDate = queryDate;
	}

	public Integer getQueryPrice() {
		return queryPrice;
	}

	public void setQueryPrice(Integer queryPrice) {
		this.queryPrice = queryPrice;
	}

	public CarUserEntity getCarUserEntity() {
		return carUserEntity;
	}

	public void setCarUserEntity(CarUserEntity carUserEntity) {
		this.carUserEntity = carUserEntity;
	}

	public String getQueryDateStr() {
		return queryDateStr;
	}

	public void setQueryDateStr(String queryDateStr) {
		this.queryDateStr = queryDateStr;
	}
}