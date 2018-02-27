package com.zl.webproject.model;

/**
 * 我的关注 （商家）(CAR_FOLLOW)
 * 
 * @author szc
 * @version 1.0.0 2017-08-06
 */
public class CarFollowEntity implements java.io.Serializable {
	/** 版本号 */
	private static final long serialVersionUID = -5105334881880761117L;

	/** 自增主键ID */
	private Integer id;

	/** 关注用户 */
	private Integer follUserId;
	
	/** 关注车行 */
	private Integer follDealerId;
	
	/** 关注时间 */
	private String follDate;

	private String follDateStr;

	/** 关注车行-实体Bean */
    private CarDealerEntity carDealerEntity;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFollUserId() {
		return follUserId;
	}

	public void setFollUserId(Integer follUserId) {
		this.follUserId = follUserId;
	}

	public Integer getFollDealerId() {
		return follDealerId;
	}

	public void setFollDealerId(Integer follDealerId) {
		this.follDealerId = follDealerId;
	}

	public String getFollDate() {
		return follDate;
	}

	public void setFollDate(String follDate) {
		this.follDate = follDate;
	}

	public String getFollDateStr() {
		return follDateStr;
	}

	public void setFollDateStr(String follDateStr) {
		this.follDateStr = follDateStr;
	}

	public CarDealerEntity getCarDealerEntity() {
		return carDealerEntity;
	}

	public void setCarDealerEntity(CarDealerEntity carDealerEntity) {
		this.carDealerEntity = carDealerEntity;
	}
}