package com.zl.webproject.model;


/**
 * 车辆品牌(CAR_BRAND)
 * 
 * @author szc
 * @version 1.0.0 2017-08-06
 */
public class CarBrandEntity implements java.io.Serializable {
	/** 版本号 */
	private static final long serialVersionUID = -8108850548058400681L;

	/** 自增主键ID */
	private Integer id;

	/** 车辆品牌名称 */
	private String brandName;

	/** 图片地址 */
	private String brandImg;

	/** 车辆排序 */
	private String brandSort;

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
	 * 获取车辆品牌名称
	 * 
	 * @return 车辆品牌名称
	 */
	public String getBrandName() {
		return this.brandName;
	}

	/**
	 * 设置车辆品牌名称
	 * 
	 * @param brandName
	 *            车辆品牌名称
	 */
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	/**
	 * 获取图片地址
	 * 
	 * @return 图片地址
	 */
	public String getBrandImg() {
		return this.brandImg;
	}

	/**
	 * 设置图片地址
	 * 
	 * @param brandImg
	 *            图片地址
	 */
	public void setBrandImg(String brandImg) {
		this.brandImg = brandImg;
	}

	/**
	 * 获取车辆排序
	 * 
	 * @return 车辆排序
	 */
	public String getBrandSort() {
		return this.brandSort;
	}

	/**
	 * 设置车辆排序
	 * 
	 * @param brandSort
	 *            车辆排序
	 */
	public void setBrandSort(String brandSort) {
		this.brandSort = brandSort;
	}
}