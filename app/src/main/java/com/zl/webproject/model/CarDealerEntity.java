package com.zl.webproject.model;

import java.util.Date;
import java.util.List;

/**
 * 车行信息(CAR_DEALER)
 * 
 * @author szc
 * @version 1.0.0 2017-08-06
 */
public class CarDealerEntity implements java.io.Serializable {
	/** 版本号 */
	private static final long serialVersionUID = 3621909744291144126L;

	/** 自增主键ID */
	private Integer id;

	/** 车行名称 */
	private String dealerName;

	/** 关联用户信息 */
	private Integer dealerUserId;

	/** 车行简称 */
	private String dealerContext;
	
	/** 车行电话 */
	private String dealerPhone;
	
	/** 车行联系人 */
	private String dealerUser;

	/** 车行头像 */
	private String dealerImg;

	/** 车行背景图 */
	private String dealerBackImg;

	/** 营业执照 */
	private String dealerBusinessLicense;

	/** 法人身份证正面 */
	private String dealerLegalIdP;

	/** 法人身份证反面 */
	private String dealerLegalIdN;

	/** 入驻时间 */
	private String dealerDate;
	
	/** 刷新时间 */
	private String dealerRefreshDate;

	/** 认证状态：0：未认证，1：认证通过 */
	private Integer dealerExamine;
	
	/** 车行所在地址 */
	private String dealerLocation;
	
	/** 车行所在城市 */
	private Integer dealerCity;
	
	/** 车行所在城市特约商 */
	private Integer dealerCityMaster;
	
	/** 会员车行  */
	private Integer dealerVip;
	
	/** 会员车行到期时间  */
	private Date dealerVipDate;
	
	/** 公司名称  */
	private String dealerCompany;
	
	/** 公司组织机构  */
	private String dealerCompanyNo;
	
	/** 公司地址  */
	private String dealerCompanyAddress;
	
	/** 公司有效期  */
	private String dealerCompanyDate;
	
	/** 法人姓名  */
	private String dealerLegal;
	
	/** 法人身份证号  */
	private String dealerLegalNo;
	
	/** 法人民族  */
	private String dealerLegalNation;
	
	/** 法人性别  */
	private String dealerLegalSex;
	
	/** 法人住址  */
	private String dealerLegalAddress;
	
	/** 车行积分 刷新一次加1分  */
	private Integer dealerSource;
	
	/** 会员积分  */
	private Integer dealerVIPSource;
	
    
	/**---------------------------------------------------------------*/
	
    /** 所在城市-实体Bean */
    private CarAreaCitysEntity city;
    
	/** 实体List<Bean> 媒体资源*/
    private List<CarDealerResourceEntity> resources;
    
    /** 实体List<Bean> 评论资源*/
    private List<CarCommentEntity> comment;
    

    private String dealerDateStr;

    private String dealerVipDateStr;

    private Integer followCount;

    private String dealerRefreshDateStr;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public Integer getDealerUserId() {
		return dealerUserId;
	}

	public void setDealerUserId(Integer dealerUserId) {
		this.dealerUserId = dealerUserId;
	}

	public String getDealerContext() {
		return dealerContext;
	}

	public void setDealerContext(String dealerContext) {
		this.dealerContext = dealerContext;
	}

	public String getDealerPhone() {
		return dealerPhone;
	}

	public void setDealerPhone(String dealerPhone) {
		this.dealerPhone = dealerPhone;
	}

	public String getDealerUser() {
		return dealerUser;
	}

	public void setDealerUser(String dealerUser) {
		this.dealerUser = dealerUser;
	}

	public String getDealerImg() {
		return dealerImg;
	}

	public void setDealerImg(String dealerImg) {
		this.dealerImg = dealerImg;
	}

	public String getDealerBackImg() {
		return dealerBackImg;
	}

	public void setDealerBackImg(String dealerBackImg) {
		this.dealerBackImg = dealerBackImg;
	}

	public String getDealerBusinessLicense() {
		return dealerBusinessLicense;
	}

	public void setDealerBusinessLicense(String dealerBusinessLicense) {
		this.dealerBusinessLicense = dealerBusinessLicense;
	}

	public String getDealerLegalIdP() {
		return dealerLegalIdP;
	}

	public void setDealerLegalIdP(String dealerLegalIdP) {
		this.dealerLegalIdP = dealerLegalIdP;
	}

	public String getDealerLegalIdN() {
		return dealerLegalIdN;
	}

	public void setDealerLegalIdN(String dealerLegalIdN) {
		this.dealerLegalIdN = dealerLegalIdN;
	}

	public String getDealerDate() {
		return dealerDate;
	}

	public void setDealerDate(String dealerDate) {
		this.dealerDate = dealerDate;
	}

	public String getDealerRefreshDate() {
		return dealerRefreshDate;
	}

	public void setDealerRefreshDate(String dealerRefreshDate) {
		this.dealerRefreshDate = dealerRefreshDate;
	}

	public Integer getDealerExamine() {
		return dealerExamine;
	}

	public void setDealerExamine(Integer dealerExamine) {
		this.dealerExamine = dealerExamine;
	}

	public String getDealerLocation() {
		return dealerLocation;
	}

	public void setDealerLocation(String dealerLocation) {
		this.dealerLocation = dealerLocation;
	}

	public Integer getDealerCity() {
		return dealerCity;
	}

	public void setDealerCity(Integer dealerCity) {
		this.dealerCity = dealerCity;
	}

	public Integer getDealerCityMaster() {
		return dealerCityMaster;
	}

	public void setDealerCityMaster(Integer dealerCityMaster) {
		this.dealerCityMaster = dealerCityMaster;
	}

	public Integer getDealerVip() {
		return dealerVip;
	}

	public void setDealerVip(Integer dealerVip) {
		this.dealerVip = dealerVip;
	}

	public Date getDealerVipDate() {
		return dealerVipDate;
	}

	public void setDealerVipDate(Date dealerVipDate) {
		this.dealerVipDate = dealerVipDate;
	}

	public String getDealerCompany() {
		return dealerCompany;
	}

	public void setDealerCompany(String dealerCompany) {
		this.dealerCompany = dealerCompany;
	}

	public String getDealerCompanyNo() {
		return dealerCompanyNo;
	}

	public void setDealerCompanyNo(String dealerCompanyNo) {
		this.dealerCompanyNo = dealerCompanyNo;
	}

	public String getDealerCompanyAddress() {
		return dealerCompanyAddress;
	}

	public void setDealerCompanyAddress(String dealerCompanyAddress) {
		this.dealerCompanyAddress = dealerCompanyAddress;
	}

	public String getDealerCompanyDate() {
		return dealerCompanyDate;
	}

	public void setDealerCompanyDate(String dealerCompanyDate) {
		this.dealerCompanyDate = dealerCompanyDate;
	}

	public String getDealerLegal() {
		return dealerLegal;
	}

	public void setDealerLegal(String dealerLegal) {
		this.dealerLegal = dealerLegal;
	}

	public String getDealerLegalNo() {
		return dealerLegalNo;
	}

	public void setDealerLegalNo(String dealerLegalNo) {
		this.dealerLegalNo = dealerLegalNo;
	}

	public String getDealerLegalNation() {
		return dealerLegalNation;
	}

	public void setDealerLegalNation(String dealerLegalNation) {
		this.dealerLegalNation = dealerLegalNation;
	}

	public String getDealerLegalSex() {
		return dealerLegalSex;
	}

	public void setDealerLegalSex(String dealerLegalSex) {
		this.dealerLegalSex = dealerLegalSex;
	}

	public String getDealerLegalAddress() {
		return dealerLegalAddress;
	}

	public void setDealerLegalAddress(String dealerLegalAddress) {
		this.dealerLegalAddress = dealerLegalAddress;
	}

	public Integer getDealerSource() {
		return dealerSource;
	}

	public void setDealerSource(Integer dealerSource) {
		this.dealerSource = dealerSource;
	}

	public Integer getDealerVIPSource() {
		return dealerVIPSource;
	}

	public void setDealerVIPSource(Integer dealerVIPSource) {
		this.dealerVIPSource = dealerVIPSource;
	}

	public CarAreaCitysEntity getCity() {
		return city;
	}

	public void setCity(CarAreaCitysEntity city) {
		this.city = city;
	}

	public List<CarDealerResourceEntity> getResources() {
		return resources;
	}

	public void setResources(List<CarDealerResourceEntity> resources) {
		this.resources = resources;
	}

	public List<CarCommentEntity> getComment() {
		return comment;
	}

	public void setComment(List<CarCommentEntity> comment) {
		this.comment = comment;
	}

	public String getDealerDateStr() {
		return dealerDateStr;
	}

	public void setDealerDateStr(String dealerDateStr) {
		this.dealerDateStr = dealerDateStr;
	}

	public String getDealerVipDateStr() {
		return dealerVipDateStr;
	}

	public void setDealerVipDateStr(String dealerVipDateStr) {
		this.dealerVipDateStr = dealerVipDateStr;
	}

	public Integer getFollowCount() {
		return followCount;
	}

	public void setFollowCount(Integer followCount) {
		this.followCount = followCount;
	}

	public String getDealerRefreshDateStr() {
		return dealerRefreshDateStr;
	}

	public void setDealerRefreshDateStr(String dealerRefreshDateStr) {
		this.dealerRefreshDateStr = dealerRefreshDateStr;
	}
}