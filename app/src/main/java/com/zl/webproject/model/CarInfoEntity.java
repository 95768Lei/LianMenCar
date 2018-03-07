package com.zl.webproject.model;

import java.util.Date;
import java.util.List;

/**
 * 车辆信息(CAR_INFO)
 *
 * @author szc
 * @version 1.0.0 2017-08-06
 */
public class CarInfoEntity implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = 3545895146919521955L;

    /**
     * 自增主键ID
     */
    private Integer id;

    /**
     * 发布人用户ID
     */
    private Integer carUserId;

    /**
     * 自编码
     */
    private String carCode;

    /**
     * 车辆状态：0：未出售；1：锁定；2：出售，3：下架
     */
    private Integer carState;

    /**
     * 展示图
     */
    private String carImg;

    /**
     * 车架号
     */
    private String carFrameCode;

    /**
     * 车牌号
     */
    private String carPlateCode;

    /**
     * 标题
     */
    private String carTitle;

    /**
     * 品牌
     */
    private Integer carBrandId;

    /**
     * 品牌
     */
    private String carBrandName;

    /**
     * 型号
     */
    private Integer carModelId;

    /**
     * 型号
     */
    private String carModelName;

    /**
     * 车辆标签 ：可过户、不可过户
     */
    private Integer carLabelType;

    /**
     * 状态标签：过户车、新车、下线车、全款无绿本、全款有绿本...
     */
    private Integer carLabel;

    /**
     * 排量
     */
    private Double carDisplacement;

    /**
     * 变速箱 ：自动、手动
     */
    private Integer carGearboxId;

    /**
     * 里程数
     */
    private Double carMileage;

    /**
     * 所在地址
     */
    private String carLocation;

    /**
     * 所在地城市 :选择城市
     */
    private Integer carLocationCitys;

    /**
     * 价格
     */
    private Double carPrice;

    /**
     * 信息
     */
    private String carContext;

    /**
     * 级别 车型：汽车等级：越野、桥车、跑车
     */
    private Integer carLv;

    /**
     * 排放标准 ：国一、国二、国三
     */
    private Integer carEmission;

    /**
     * 车辆颜色
     */
    private Integer carColor;

    /**
     * 座位数
     */
    private Integer carSeating;

    /**
     * 燃油类型
     */
    private Integer carFuel;

    /**
     * 国别
     */
    private Integer carCountry;

    /**
     * 制造方式
     */
    private Integer carManufacture;

    /**
     * 车源信息：0：个人；N商家
     */
    private Integer carSource;

    /**
     * 用户手机号
     */
    private String userPhone;


    /**
     * 定金
     */
    private Double carDeposit;

    /**
     * 佣金
     */
    private Double carCommission;

    /**
     * 锁定
     */
    private Integer carLocking;

    /**
     * 查封
     */
    private Integer carSeized;

    /**
     * 违章
     */
    private Integer carPeccancy;

    /**
     * 访问量
     */
    private Integer carBrowse;

    /**
     * 转发量
     */
    private Integer carForWard;

    /**
     * 热度
     */
    private Integer carCollectionCount;

    public Integer getCarCollectionCount() {
        return carCollectionCount;
    }

    public void setCarCollectionCount(Integer carCollectionCount) {
        this.carCollectionCount = carCollectionCount;
    }

    /**---------------------------------------------------------------------*/

    /**
     * 标签-实体Bean
     */
    private CarDictionaryEntity labelType;

    /**
     * 标签-实体Bean
     */
    private CarDictionaryEntity label;

    /** 品牌-实体Bean 
     @ManyToOne(cascade = CascadeType.REFRESH, fetch=FetchType.EAGER)
     @JoinColumn(name="CAR_BRAND_ID" , nullable = true,insertable=false,updatable=false)//这2个属性保证 不会插入对象，只对查询有效
     private CarBrandEntity carBrandEntity;*/

    /** 型号-实体Bean
     @ManyToOne(cascade = CascadeType.REFRESH, fetch=FetchType.EAGER)
     @JoinColumn(name="CAR_MODEL_ID" , nullable = true,insertable=false,updatable=false)//这2个属性保证 不会插入对象，只对查询有效
     private CarModelEntity carModelEntity; */

    /**
     * 变速箱-实体Bean
     */
    private CarDictionaryEntity gearbox;

    /**
     * 级别-实体Bean
     */
    private CarDictionaryEntity lv;

    /** 排放标准-实体Bean 
     @ManyToOne(cascade = CascadeType.REFRESH, fetch=FetchType.EAGER)
     @JoinColumn(name="CAR_EMISSION" , nullable = true,insertable=false,updatable=false)//这2个属性保证 不会插入对象，只对查询有效
     private CarDictionaryEntity emission;*/

    /** 车辆颜色-实体Bean 
     @ManyToOne(cascade = CascadeType.REFRESH, fetch=FetchType.EAGER)
     @JoinColumn(name="CAR_COLOR" , nullable = true,insertable=false,updatable=false)//这2个属性保证 不会插入对象，只对查询有效
     private CarDictionaryEntity color;*/

    /** 座位数-实体Bean 
     @ManyToOne(cascade = CascadeType.REFRESH, fetch=FetchType.EAGER)
     @JoinColumn(name="CAR_SEATING" , nullable = true,insertable=false,updatable=false)//这2个属性保证 不会插入对象，只对查询有效
     private CarDictionaryEntity seating;*/

    /**
     * 燃油类型-实体Bean
     */
    private CarDictionaryEntity fuel;

    /** 国别-实体Bean
     @ManyToOne(cascade = CascadeType.REFRESH, fetch=FetchType.EAGER)
     @JoinColumn(name="CAR_COUNTRY" , nullable = true,insertable=false,updatable=false)//这2个属性保证 不会插入对象，只对查询有效
     private CarDictionaryEntity country; */

    /** 制造方式-实体Bean 
     @ManyToOne(cascade = CascadeType.REFRESH, fetch=FetchType.EAGER)
     @JoinColumn(name="CAR_MANUFACTURE" , nullable = true,insertable=false,updatable=false)//这2个属性保证 不会插入对象，只对查询有效
     private CarDictionaryEntity manufacture; */

    /**
     * 所在地城市-实体Bean
     */
    private CarAreaCitysEntity carAreaCitysEntity;

    /**
     * 获得用户信息-实体Bean
     */
    private CarUserEntity carUserEntity;

    /**
     * 实体List<Bean> 媒体资源
     */
    private List<CarResourceEntity> resources;

    /**
     * -----------------------------------------------------------------
     */
    private String carDateStr;


    private String carRefreshDateStr;

    private String carLicensingDateStr;

    private Integer collectionCount;

    private String newCarImg;

    /**
     * -----------------------------------------------------------------
     */

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCarUserId() {
        return carUserId;
    }

    public void setCarUserId(Integer carUserId) {
        this.carUserId = carUserId;
    }

    public String getCarCode() {
        return carCode;
    }

    public void setCarCode(String carCode) {
        this.carCode = carCode;
    }

    public Integer getCarState() {
        return carState;
    }

    public void setCarState(Integer carState) {
        this.carState = carState;
    }

    public String getCarImg() {
        return carImg;
    }

    public void setCarImg(String carImg) {
        this.carImg = carImg;
    }

    public String getCarFrameCode() {
        return carFrameCode;
    }

    public void setCarFrameCode(String carFrameCode) {
        this.carFrameCode = carFrameCode;
    }

    public String getCarPlateCode() {
        return carPlateCode;
    }

    public void setCarPlateCode(String carPlateCode) {
        this.carPlateCode = carPlateCode;
    }

    public String getCarTitle() {
        return carTitle;
    }

    public void setCarTitle(String carTitle) {
        this.carTitle = carTitle;
    }

    public Integer getCarBrandId() {
        return carBrandId;
    }

    public void setCarBrandId(Integer carBrandId) {
        this.carBrandId = carBrandId;
    }

    public String getCarBrandName() {
        return carBrandName;
    }

    public void setCarBrandName(String carBrandName) {
        this.carBrandName = carBrandName;
    }

    public Integer getCarModelId() {
        return carModelId;
    }

    public void setCarModelId(Integer carModelId) {
        this.carModelId = carModelId;
    }

    public String getCarModelName() {
        return carModelName;
    }

    public void setCarModelName(String carModelName) {
        this.carModelName = carModelName;
    }

    public Integer getCarLabelType() {
        return carLabelType;
    }

    public void setCarLabelType(Integer carLabelType) {
        this.carLabelType = carLabelType;
    }

    public Integer getCarLabel() {
        return carLabel;
    }

    public void setCarLabel(Integer carLabel) {
        this.carLabel = carLabel;
    }

    public Double getCarDisplacement() {
        return carDisplacement;
    }

    public void setCarDisplacement(Double carDisplacement) {
        this.carDisplacement = carDisplacement;
    }

    public Integer getCarGearboxId() {
        return carGearboxId;
    }

    public void setCarGearboxId(Integer carGearboxId) {
        this.carGearboxId = carGearboxId;
    }

    public Double getCarMileage() {
        return carMileage;
    }

    public void setCarMileage(Double carMileage) {
        this.carMileage = carMileage;
    }

    public String getCarLocation() {
        return carLocation;
    }

    public void setCarLocation(String carLocation) {
        this.carLocation = carLocation;
    }

    public Integer getCarLocationCitys() {
        return carLocationCitys;
    }

    public void setCarLocationCitys(Integer carLocationCitys) {
        this.carLocationCitys = carLocationCitys;
    }

    public Double getCarPrice() {
        return carPrice;
    }

    public void setCarPrice(Double carPrice) {
        this.carPrice = carPrice;
    }

    public String getCarContext() {
        return carContext;
    }

    public void setCarContext(String carContext) {
        this.carContext = carContext;
    }

    public Integer getCarLv() {
        return carLv;
    }

    public void setCarLv(Integer carLv) {
        this.carLv = carLv;
    }

    public Integer getCarEmission() {
        return carEmission;
    }

    public void setCarEmission(Integer carEmission) {
        this.carEmission = carEmission;
    }

    public Integer getCarColor() {
        return carColor;
    }

    public void setCarColor(Integer carColor) {
        this.carColor = carColor;
    }

    public Integer getCarSeating() {
        return carSeating;
    }

    public void setCarSeating(Integer carSeating) {
        this.carSeating = carSeating;
    }

    public Integer getCarFuel() {
        return carFuel;
    }

    public void setCarFuel(Integer carFuel) {
        this.carFuel = carFuel;
    }

    public Integer getCarCountry() {
        return carCountry;
    }

    public void setCarCountry(Integer carCountry) {
        this.carCountry = carCountry;
    }

    public Integer getCarManufacture() {
        return carManufacture;
    }

    public void setCarManufacture(Integer carManufacture) {
        this.carManufacture = carManufacture;
    }

    public Integer getCarSource() {
        return carSource;
    }

    public void setCarSource(Integer carSource) {
        this.carSource = carSource;
    }

    public Double getCarDeposit() {
        return carDeposit;
    }

    public void setCarDeposit(Double carDeposit) {
        this.carDeposit = carDeposit;
    }

    public Double getCarCommission() {
        return carCommission;
    }

    public void setCarCommission(Double carCommission) {
        this.carCommission = carCommission;
    }

    public Integer getCarLocking() {
        return carLocking;
    }

    public void setCarLocking(Integer carLocking) {
        this.carLocking = carLocking;
    }

    public Integer getCarSeized() {
        return carSeized;
    }

    public void setCarSeized(Integer carSeized) {
        this.carSeized = carSeized;
    }

    public Integer getCarPeccancy() {
        return carPeccancy;
    }

    public void setCarPeccancy(Integer carPeccancy) {
        this.carPeccancy = carPeccancy;
    }

    public Integer getCarBrowse() {
        return carBrowse;
    }

    public void setCarBrowse(Integer carBrowse) {
        this.carBrowse = carBrowse;
    }

    public Integer getCarForWard() {
        return carForWard;
    }

    public void setCarForWard(Integer carForWard) {
        this.carForWard = carForWard;
    }

    public CarDictionaryEntity getLabelType() {
        return labelType;
    }

    public void setLabelType(CarDictionaryEntity labelType) {
        this.labelType = labelType;
    }

    public CarDictionaryEntity getLabel() {
        return label;
    }

    public void setLabel(CarDictionaryEntity label) {
        this.label = label;
    }

    public CarDictionaryEntity getGearbox() {
        return gearbox;
    }

    public void setGearbox(CarDictionaryEntity gearbox) {
        this.gearbox = gearbox;
    }

    public CarDictionaryEntity getLv() {
        return lv;
    }

    public void setLv(CarDictionaryEntity lv) {
        this.lv = lv;
    }

    public CarDictionaryEntity getFuel() {
        return fuel;
    }

    public void setFuel(CarDictionaryEntity fuel) {
        this.fuel = fuel;
    }

    public CarAreaCitysEntity getCarAreaCitysEntity() {
        return carAreaCitysEntity;
    }

    public void setCarAreaCitysEntity(CarAreaCitysEntity carAreaCitysEntity) {
        this.carAreaCitysEntity = carAreaCitysEntity;
    }

    public CarUserEntity getCarUserEntity() {
        return carUserEntity;
    }

    public void setCarUserEntity(CarUserEntity carUserEntity) {
        this.carUserEntity = carUserEntity;
    }

    public List<CarResourceEntity> getResources() {
        return resources;
    }

    public void setResources(List<CarResourceEntity> resources) {
        this.resources = resources;
    }

    public String getCarDateStr() {
        return carDateStr;
    }

    public void setCarDateStr(String carDateStr) {
        this.carDateStr = carDateStr;
    }

    public String getCarRefreshDateStr() {
        return carRefreshDateStr;
    }

    public void setCarRefreshDateStr(String carRefreshDateStr) {
        this.carRefreshDateStr = carRefreshDateStr;
    }

    public String getCarLicensingDateStr() {
        return carLicensingDateStr;
    }

    public void setCarLicensingDateStr(String carLicensingDateStr) {
        this.carLicensingDateStr = carLicensingDateStr;
    }

    public Integer getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(Integer collectionCount) {
        this.collectionCount = collectionCount;
    }

    public String getNewCarImg() {
        return newCarImg;
    }

    public void setNewCarImg(String newCarImg) {
        this.newCarImg = newCarImg;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}