package com.zl.webproject.model;

import java.util.Date;

/**
 * 车辆评价信息表(CAR_COMMENT)
 * 
 * @author szc
 * @version 1.0.0 2017-08-06
 */
public class CarCommentEntity implements java.io.Serializable {
	/** 版本号 */
	private static final long serialVersionUID = -7428592548966655143L;

	/** 自增主键ID */
	private Integer id;

	/** 关联车行ID */
	private Integer commDealerId;

	/** 关联用户ID */
	private Integer commUserId;

	/** 评论信息内容 */
	private String commContext;

	/** 评论日期 */
	private Date commDate;

	/** 评分 */
	private Integer commScore;

	/** 评论审核；0：待审核；1：审核通过；2：不通过 */
	private Integer commExamine;

	/** 用户-实体Bean */
	private CarUserEntity carUserEntity;

	private String commDateStr;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCommDealerId() {
		return this.commDealerId;
	}

	public void setCommDealerId(Integer commDealerId) {
		this.commDealerId = commDealerId;
	}

	public Integer getCommUserId() {
		return this.commUserId;
	}

	public void setCommUserId(Integer commUserId) {
		this.commUserId = commUserId;
	}

	public String getCommContext() {
		return this.commContext;
	}

	public void setCommContext(String commContext) {
		this.commContext = commContext;
	}

	public Date getCommDate() {
		return commDate;
	}

	public void setCommDate(Date commDate) {
		this.commDate = commDate;
	}

	public Integer getCommScore() {
		return this.commScore;
	}

	public void setCommScore(Integer commScore) {
		this.commScore = commScore;
	}

	public Integer getCommExamine() {
		return this.commExamine;
	}

	public void setCommExamine(Integer commExamine) {
		this.commExamine = commExamine;
	}

	public CarUserEntity getCarUserEntity() {
		return carUserEntity;
	}

	public void setCarUserEntity(CarUserEntity carUserEntity) {
		this.carUserEntity = carUserEntity;
	}

	public String getCommDateStr() {
		return commDateStr;
	}

	public void setCommDateStr(String commDateStr) {
		this.commDateStr = commDateStr;
	}
}