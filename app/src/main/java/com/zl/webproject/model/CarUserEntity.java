package com.zl.webproject.model;

/**
 * 用户(CAR_USER)
 *
 * @author szc
 * @version 1.0.0 2017-08-06
 */
public class CarUserEntity implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = 1118408154772303677L;

    /**
     * 主键自增ID
     */
    private Integer id = 0;

    /**
     * 用户类型：0，个人；1:中介；2：商家；
     */
    private String userState;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 用户身份证号
     */
    private String userNo;

    /**
     * 用户性别
     */
    private Integer userSex = 1;

    /**
     * 用户昵称
     */
    private String userNikeName;

    /**
     * 联合登陆QQ
     */
    private String userLoginQq;

    /**
     * 联合登陆微信
     */
    private String userLoginWechat;

    /**
     * 用户手机号
     */
    private String userPhone;

    /**
     * 用户密码
     */
    private String userPassword;

    /**
     * 用户生日
     */
    private String userDate;

    /**
     * 登陆位置（定位）
     */
    private String userLocation;

    /**
     * 创建日期
     */
    private String userCreateDate;

    /**
     * 最后一次登陆时间
     */
    private String userCreateDateStr;

    /**
     * 最后一次登陆时间
     */
    private String userLoginDate;

    /**
     * 最后一次登陆时间
     */
    private String userLoginDateStr;

    /**
     * 头像照片
     */
    private String userImg;

    /**
     * 车行ID
     */
    private Integer carDealerId = -1;

    /**
     * 城市地址
     */
    private Integer userCityId = 0;

    /**
     * 推送码
     */
    private String userPustCode;

    /**
     * 完善信息表
     */
    private Integer userInter = 0;

    /**
     * 申请认证中介 0：未申请，1：申请
     */
    private Integer userApply = 0;

    /** 车行认证 0：未认证，1：认证 */
    private Integer userDealerApply = 0;

    public Integer getUserDealerApply() {
        return userDealerApply;
    }

    public void setUserDealerApply(Integer userDealerApply) {
        this.userDealerApply = userDealerApply;
    }

    /**
     * 完善信息表
     */
    private CarIntermediaryEntity inter;

    private CarDealerEntity dealer;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public Integer getUserSex() {
        return userSex;
    }

    public void setUserSex(Integer userSex) {
        this.userSex = userSex;
    }

    public String getUserNikeName() {
        return userNikeName;
    }

    public void setUserNikeName(String userNikeName) {
        this.userNikeName = userNikeName;
    }

    public String getUserLoginQq() {
        return userLoginQq;
    }

    public void setUserLoginQq(String userLoginQq) {
        this.userLoginQq = userLoginQq;
    }

    public String getUserLoginWechat() {
        return userLoginWechat;
    }

    public void setUserLoginWechat(String userLoginWechat) {
        this.userLoginWechat = userLoginWechat;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserDate() {
        return userDate;
    }

    public void setUserDate(String userDate) {
        this.userDate = userDate;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public String getUserCreateDate() {
        return userCreateDate;
    }

    public void setUserCreateDate(String userCreateDate) {
        this.userCreateDate = userCreateDate;
    }

    public String getUserCreateDateStr() {
        return userCreateDateStr;
    }

    public void setUserCreateDateStr(String userCreateDateStr) {
        this.userCreateDateStr = userCreateDateStr;
    }

    public String getUserLoginDate() {
        return userLoginDate;
    }

    public void setUserLoginDate(String userLoginDate) {
        this.userLoginDate = userLoginDate;
    }

    public String getUserLoginDateStr() {
        return userLoginDateStr;
    }

    public void setUserLoginDateStr(String userLoginDateStr) {
        this.userLoginDateStr = userLoginDateStr;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public Integer getCarDealerId() {
        return carDealerId;
    }

    public void setCarDealerId(Integer carDealerId) {
        this.carDealerId = carDealerId;
    }

    public Integer getUserCityId() {
        return userCityId;
    }

    public void setUserCityId(Integer userCityId) {
        this.userCityId = userCityId;
    }

    public String getUserPustCode() {
        return userPustCode;
    }

    public void setUserPustCode(String userPustCode) {
        this.userPustCode = userPustCode;
    }

    public Integer getUserInter() {
        return userInter;
    }

    public void setUserInter(Integer userInter) {
        this.userInter = userInter;
    }

    public Integer getUserApply() {
        return userApply;
    }

    public void setUserApply(Integer userApply) {
        this.userApply = userApply;
    }

    public CarIntermediaryEntity getInter() {
        return inter;
    }

    public void setInter(CarIntermediaryEntity inter) {
        this.inter = inter;
    }

    public CarDealerEntity getDealer() {
        return dealer;
    }

    public void setDealer(CarDealerEntity dealer) {
        this.dealer = dealer;
    }
}