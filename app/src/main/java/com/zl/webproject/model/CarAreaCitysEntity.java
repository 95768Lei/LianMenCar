package com.zl.webproject.model;


/**
 * 城市(CAR_AREA_CITYS)
 * 
 * @author szc
 * @version 1.0.0 2017-08-06
 */
public class CarAreaCitysEntity implements java.io.Serializable {
	/** 版本号 */
	private static final long serialVersionUID = 2779454102765188942L;

	/** 自增主键ID */
	private Integer id;

	/** 城市编码 */
	private String cityCode;

	/** 城市名称 */
	private String cityName;

	/** 首字母 */
	private String citySort;

	/** 简拼 */
	private String cityShort;


	public CarAreaCitysEntity() {
		// TODO Auto-generated constructor stub
	}

	public CarAreaCitysEntity(String cityCode,String cityName,String citySort,String cityShort) {
		this.cityCode=cityCode;
		this.cityName=cityName;
		this.citySort=citySort;
		this.cityShort=cityShort;
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
	 * 获取城市编码
	 *
	 * @return 城市编码
	 */
	public String getCityCode() {
		return this.cityCode;
	}

	/**
	 * 设置城市编码
	 *
	 * @param cityCode
	 *            城市编码
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	/**
	 * 获取城市名称
	 *
	 * @return 城市名称
	 */
	public String getCityName() {
		return this.cityName;
	}

	/**
	 * 设置城市名称
	 *
	 * @param cityName
	 *            城市名称
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * 获取首字母
	 *
	 * @return 首字母
	 */
	public String getCitySort() {
		return this.citySort;
	}

	/**
	 * 设置首字母
	 *
	 * @param citySort
	 *            首字母
	 */
	public void setCitySort(String citySort) {
		this.citySort = citySort;
	}

	/**
	 * 获取简拼
	 *
	 * @return 简拼
	 */
	public String getCityShort() {
		return cityShort;
	}

	/**
	 * 设置简拼
	 *
	 * @param cityShort
	 *            简拼
	 */
	public void setCityShort(String cityShort) {
		this.cityShort = cityShort;
	}
}