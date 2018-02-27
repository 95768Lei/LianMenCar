package com.zl.webproject.model;

/**
 * 信息转发表(CAR_FORWARD)
 * 
 * @author szc
 * @version 1.0.0 2017-08-06
 */
public class CarForwardEntity implements java.io.Serializable {
	/** 版本号 */
	private static final long serialVersionUID = 5998821126301754987L;

	/** 自增主键ID */
	private Integer id;

	/** 关联车辆信息表 */

	private Integer forCarId;

	/** 关联用户信息表 */
	private Integer forUserId;
	
	/** 关联转发用户信息表 */
	private Integer toUserId;
	
	/** 关联转发次数 */
	private Integer forCount;

	/** 转发时间 */
	private String forDate;
	
	private String forDateStr;

	/** 关联车辆信息表-实体Bean */
    private CarInfoEntity carInfoEntity;
    
    /** 关联转发的用户信息表-实体Bean */
    private CarUserEntity carUserEntity;
    
    /**--------------------------------------------------------------*/

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getForCarId() {
		return forCarId;
	}

	public void setForCarId(Integer forCarId) {
		this.forCarId = forCarId;
	}

	public Integer getForUserId() {
		return forUserId;
	}

	public void setForUserId(Integer forUserId) {
		this.forUserId = forUserId;
	}

	public Integer getToUserId() {
		return toUserId;
	}

	public void setToUserId(Integer toUserId) {
		this.toUserId = toUserId;
	}

	public Integer getForCount() {
		return forCount;
	}

	public void setForCount(Integer forCount) {
		this.forCount = forCount;
	}

	public String getForDate() {
		return forDate;
	}

	public void setForDate(String forDate) {
		this.forDate = forDate;
	}

	public String getForDateStr() {
		return forDateStr;
	}

	public void setForDateStr(String forDateStr) {
		this.forDateStr = forDateStr;
	}

	public CarInfoEntity getCarInfoEntity() {
		return carInfoEntity;
	}

	public void setCarInfoEntity(CarInfoEntity carInfoEntity) {
		this.carInfoEntity = carInfoEntity;
	}

	public CarUserEntity getCarUserEntity() {
		return carUserEntity;
	}

	public void setCarUserEntity(CarUserEntity carUserEntity) {
		this.carUserEntity = carUserEntity;
	}
    
}