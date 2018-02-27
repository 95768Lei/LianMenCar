package com.zl.webproject.model;

import java.util.Date;

/**
 * 意见反馈(CAR_FEEDBACK)
 * 
 * @author szc
 * @version 1.0.0 2017-08-06
 */

public class CarFeedbackEntity implements java.io.Serializable {
	/** 版本号 */
	private static final long serialVersionUID = 2779454102765188942L;

	/** 自增主键ID */
	private Integer id;

	/** 发布时间  */
	private Date feedbackDate;

	/** 发布内容 */
	private String feedbackContext;

	/** 发布用户 */
	private Integer feedbackUserId;
	
	/** 采纳 */
	private Integer feedbackApply;
	
	/** 用户-实体Bean */
    private CarUserEntity carUserEntity;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getFeedbackDate() {
		return feedbackDate;
	}

	public void setFeedbackDate(Date feedbackDate) {
		this.feedbackDate = feedbackDate;
	}

	public String getFeedbackContext() {
		return feedbackContext;
	}

	public void setFeedbackContext(String feedbackContext) {
		this.feedbackContext = feedbackContext;
	}

	public Integer getFeedbackUserId() {
		return feedbackUserId;
	}

	public void setFeedbackUserId(Integer feedbackUserId) {
		this.feedbackUserId = feedbackUserId;
	}

	public CarUserEntity getCarUserEntity() {
		return carUserEntity;
	}

	public void setCarUserEntity(CarUserEntity carUserEntity) {
		this.carUserEntity = carUserEntity;
	}

	public Integer getFeedbackApply() {
		return feedbackApply;
	}

	public void setFeedbackApply(Integer feedbackApply) {
		this.feedbackApply = feedbackApply;
	}
}