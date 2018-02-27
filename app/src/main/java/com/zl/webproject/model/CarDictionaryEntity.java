package com.zl.webproject.model;

import java.util.List;

/**
 * 汽车数据字典(CAR_DICTIONARY)
 * 
 * @author szc
 * @version 1.0.0 2017-08-06
 */
public class CarDictionaryEntity implements java.io.Serializable {
	/** 版本号 */
	private static final long serialVersionUID = -1214031766998387859L;

	/** 自增主键ID */
	private Integer id;

	/** 字典名称 */
	private String dictName;

	/** 上级字典 */
	private Integer pId;

	/** 字典分类key */
	private String dictKey;
	
	/** 字典备注信息 */
	private String dictText;

	private List<CarDictionaryEntity> dictionaryList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public String getDictKey() {
		return dictKey;
	}

	public void setDictKey(String dictKey) {
		this.dictKey = dictKey;
	}

	public String getDictText() {
		return dictText;
	}

	public void setDictText(String dictText) {
		this.dictText = dictText;
	}

	public List<CarDictionaryEntity> getDictionaryList() {
		return dictionaryList;
	}

	public void setDictionaryList(List<CarDictionaryEntity> dictionaryList) {
		this.dictionaryList = dictionaryList;
	}
}