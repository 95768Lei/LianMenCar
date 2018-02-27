package com.zl.webproject.model;


/**
 * 车行资源信息(CAR_DEALER_RESOURCE)
 * 
 * @author szc
 * @version 1.0.0 2017-08-06
 */
public class CarDealerResourceEntity implements java.io.Serializable {
	/** 版本号 */
	private static final long serialVersionUID = 150065664521679740L;

	/** 自增主键ID */
	private Integer id;

	/** 关联车行信息 */
	private Integer resDealerId;

	/** 关联车行图片资源 */
	private String resDealerUrl;
	
	public CarDealerResourceEntity() {
	}
	
	public CarDealerResourceEntity(Integer resDealerId, String resDealerUrl) {
		this.resDealerId=resDealerId;
		this.resDealerUrl=resDealerUrl;
	}

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
	 * 获取关联车行信息
	 * 
	 * @return 关联车行信息
	 */
	public Integer getResDealerId() {
		return this.resDealerId;
	}

	/**
	 * 设置关联车行信息
	 * 
	 * @param resDealerId
	 *            关联车行信息
	 */
	public void setResDealerId(Integer resDealerId) {
		this.resDealerId = resDealerId;
	}

	/**
	 * 获取关联车行图片资源
	 * 
	 * @return 关联车辆图片资源
	 */
	public String getResDealerUrl() {
		return this.resDealerUrl;
	}

	/**
	 * 设置关联车行图片资源
	 *
	 *            关联车行图片资源
	 */
	public void setResDealerUrl(String resDealerUrl) {
		this.resDealerUrl = resDealerUrl;
	}

}