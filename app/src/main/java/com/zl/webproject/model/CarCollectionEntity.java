package com.zl.webproject.model;

/**
 * 我的收藏车辆(CAR_COLLECTION)
 * 
 * @author szc
 * @version 1.0.0 2017-08-06
 */
public class CarCollectionEntity implements java.io.Serializable {
	/** 版本号 */
	private static final long serialVersionUID = 3805576093454993689L;

	/** 自增主键ID */
	private Integer id;

	/** 关联收藏人ID */
	private Integer collUserId;

	/** 车辆ID */
	private Integer collCarId;

	/** 收藏时间 */
	private String collDate;

	private String collDateStr;

	/** 车辆ID-实体Bean */
	private CarInfoEntity carInfoEntity;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCollUserId() {
		return collUserId;
	}

	public void setCollUserId(Integer collUserId) {
		this.collUserId = collUserId;
	}

	public Integer getCollCarId() {
		return collCarId;
	}

	public void setCollCarId(Integer collCarId) {
		this.collCarId = collCarId;
	}

	public String getCollDate() {
		return collDate;
	}

	public void setCollDate(String collDate) {
		this.collDate = collDate;
	}

	public String getCollDateStr() {
		return collDateStr;
	}

	public void setCollDateStr(String collDateStr) {
		this.collDateStr = collDateStr;
	}

	public CarInfoEntity getCarInfoEntity() {
		return carInfoEntity;
	}

	public void setCarInfoEntity(CarInfoEntity carInfoEntity) {
		this.carInfoEntity = carInfoEntity;
	}
}