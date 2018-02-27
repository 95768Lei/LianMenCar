package com.zl.webproject.model;

/**
 * 用户(CAR_USER)
 * 
 * @author szc
 * @version 1.0.0 2017-08-06
 */
public class CarUserEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 1118408154772303677L;
    
    /** 主键自增ID */
    private Integer id;
    
    /** 用户类型：0，个人；1:中介；2：商家；*/
    private String userState;
    
    /** 用户姓名 */
    private String userName;
    
    /** 用户身份证号 */
    private String userNo;
    
    /** 用户性别 */
    private Integer userSex;
    
    /** 用户昵称 */
    private String userNikeName;
    
    /** 联合登陆QQ */
    private String userLoginQq;
    
    /** 联合登陆微信 */
    private String userLoginWechat;
    
    /** 用户手机号 */
    private String userPhone;
    
    /** 用户密码 */
    private String userPassword;
    
    /** 用户生日 */
    private String userDate;
    
    /** 登陆位置（定位） */
    private String userLocation;
    
    /** 创建日期 */
    private String userCreateDate;
    
    /** 最后一次登陆时间 */
    private String userCreateDateStr;
    
    /** 最后一次登陆时间 */
    private String userLoginDate;
    
    /** 最后一次登陆时间 */
    private String userLoginDateStr;
    
    /** 头像照片 */
    private String userImg;
    
    /**  车行ID */
    private Integer carDealerId;
    
    /** 城市地址 */
    private Integer userCityId;
    
    /** 推送码*/
    private String userPustCode;
    
    /** 完善信息表 */
    private Integer userInter;
    
    /** 申请认证中介 0：未申请，1：申请 */
    private Integer userApply;
    
    /** 完善信息表 */
    private CarIntermediaryEntity inter;

    private CarDealerEntity dealer;
	
}