package com.zl.webproject.model;

/**
 * 车辆类型(CAR_MODEL)
 * 
 * @author szc
 * @version 1.0.0 2017-08-06
 */
public class CarModelEntity implements java.io.Serializable {
	/** 版本号 */
	private static final long serialVersionUID = 2488809146687085999L;

	/** 自增主键ID */
	private Integer id;

	/** 车辆类型 */
	private String modelName;

	/** 所属品牌ID */
	private Integer modelBrandId;

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
	 * 获取车辆类型
	 * 
	 * @return 车辆类型
	 */
	public String getModelName() {
		return this.modelName;
	}

	/**
	 * 设置车辆类型
	 * 
	 * @param modelName
	 *            车辆类型
	 */
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	/**
	 * 获取所属品牌ID
	 * 
	 * @return 所属品牌ID
	 */
	public Integer getModelBrandId() {
		return this.modelBrandId;
	}

	/**
	 * 设置所属品牌ID
	 * 
	 * @param modelBrandId
	 *            所属品牌ID
	 */
	public void setModelBrandId(Integer modelBrandId) {
		this.modelBrandId = modelBrandId;
	}
}