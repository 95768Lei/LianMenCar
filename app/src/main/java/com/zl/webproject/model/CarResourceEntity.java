package com.zl.webproject.model;


/**
 * 车辆资源信息(CAR_RESOURCE)
 * 
 * @author szc
 * @version 1.0.0 2017-08-06
 */
public class CarResourceEntity implements java.io.Serializable {
	/** 版本号 */
	private static final long serialVersionUID = 150065664521679740L;

	/** 自增主键ID */
	private Integer id;

	/** 关联车辆信息 */
	private Integer resCarId;

	/** 关联车辆图片资源 */
	private String resUrl;

	/** 资源类型：0：图片；1视频； */
	private Integer resType;

	
	/**
	 * 获取自增主键ID
	 * 
	 * @return 自增主键ID
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * 设置自增主键ID
	 * 
	 * @param id
	 *            自增主键ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 获取关联车辆信息
	 * 
	 * @return 关联车辆信息
	 */
	public Integer getResCarId() {
		return this.resCarId;
	}

	/**
	 * 设置关联车辆信息
	 * 
	 * @param resCarId
	 *            关联车辆信息
	 */
	public void setResCarId(Integer resCarId) {
		this.resCarId = resCarId;
	}

	/**
	 * 获取关联车辆图片资源
	 * 
	 * @return 关联车辆图片资源
	 */
	public String getResUrl() {
		return this.resUrl;
	}

	/**
	 * 设置关联车辆图片资源
	 * 
	 * @param resUrl
	 *            关联车辆图片资源
	 */
	public void setResUrl(String resUrl) {
		this.resUrl = resUrl;
	}

	/**
	 * 获取资源类型：0：图片；1视频；
	 * 
	 * @return 资源类型
	 */
	public Integer getResType() {
		return this.resType;
	}

	/**
	 * 设置资源类型：0：图片；1视频；
	 * 
	 * @param resType
	 *            资源类型：0：图片；1视频；
	 */
	public void setResType(Integer resType) {
		this.resType = resType;
	}
}