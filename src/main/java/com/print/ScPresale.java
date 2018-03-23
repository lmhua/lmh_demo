package com.print;

import java.io.Serializable;
import java.util.List;

/**
 * 商品服务定金预售
 * 
 * @author PengHuali
 *
 */
public class ScPresale implements Serializable {

    /**
     * 序列化版本标识
     */
    private static final long serialVersionUID = -8825876808529593387L;

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 会员账户id
     */
    private Integer subaccountId;

    /**
     * 付费类型 0-定金 1-全额付款
     */
    private Integer payType;

    /**
     * 应需总额
     */
    private Double shouldPay;

    /**
     * 实需总额
     */
    private Double actualPay;

    /**
     * 预订时间
     */
    private String consumeTime;

    /**
     * 预付款
     */
    private Double imprest;

    /**
     * 未付余款
     */
    private Double balance;

    /**
     * 销售员
     */
    private String salesman;

    /**
     * 销售员id
     */
    private String salesmanId;

    /**
     * 操作员
     */
    private String operator;
    
    /**
     * 操作员id
     */
    private Integer operatorId;

    /**
     * 门店id
     */
    private Integer shopId;

    /**
     * 商户id
     */
    private Integer merchantId;
    
    /**
     * 订单状态：订单状态: 0-待处理；1-处理中；2-待支付；3-已支付；
     */
    private Integer orderStatus;

    /**
     * 付款状态 0-未完成支付（未处理） 1-已完成支付（已支付，全额付款时为已完成支付）新增状态：2-处理中（服务中）3-待支付（服务完成未支付）
     */
    private Integer payStatus;

    /**
     * 交付状态0-未交付 1-已交付
     */
    private Integer deliverStatus;

    /**
     * 会员名称
     */
    private String accountName;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 账户类型
     */
    private String businessName;
    
    /**
     * 办理业务id
     */
    private Integer businessId;

    /**
     * 桌位编号
     */
    private Integer facilityId;
    
    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 备注
     */
    private String remake;
    
    /**
     * 桌号名
     */
    private String facilityName;
    
    /**
     * 会员卡中本金
     */
    private Double principal;
    
    /**
     * 消费类型 1-商品消费 2-服务消费
     */
    private Integer type;

    /**
     * 会员折扣
     */
    private Integer discount;
    
    /**
     * 活动ID
     */
    private Integer promotionId;
    
    /**
     * 整单优惠金额
     */
    private String discountMoney;
    
    private List<ScPresaleInfo> scPresaleInfoList;
    
    /**
     *  0-计算提成
     */
    private Integer isAch;
    
    /**
     * 0-打折, 1-不打折
     */
    private Integer isRebate;
    
    /**
     * 兑换码id
     */
    private Integer cashId;
    
    /**
     *新增三个订单处理状态时间字段
     */
    private String operateTime;//处理时间-对应状态为处理中
    private String finishTime;//完成时间-对应状态为待支付
    private String payTime;//支付时间-对应状态为已支付
    
    private Integer appraise;//用户评价0-代表未评价；1-代表差；2-代表一般；3-代表良；4-代表优
    
    private Integer userId;//用户ID
    
    private Integer specified; //是否参加特殊活动 0-否 1-是

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSubaccountId() {
        return subaccountId;
    }

    public void setSubaccountId(Integer subaccountId) {
        this.subaccountId = subaccountId;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Double getShouldPay() {
        return shouldPay;
    }

    public void setShouldPay(Double shouldPay) {
        this.shouldPay = shouldPay;
    }

    public Double getActualPay() {
        return actualPay;
    }

    public void setActualPay(Double actualPay) {
        this.actualPay = actualPay;
    }

    public String getConsumeTime() {
        return consumeTime;
    }

    public void setConsumeTime(String consumeTime) {
        this.consumeTime = consumeTime;
    }

    public Double getImprest() {
        return imprest;
    }

    public void setImprest(Double imprest) {
        this.imprest = imprest;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getSalesman() {
        return salesman;
    }

    public void setSalesman(String salesman) {
        this.salesman = salesman;
    }

    public String getSalesmanId() {
        return salesmanId;
    }

    public void setSalesmanId(String salesmanId) {
        this.salesmanId = salesmanId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Integer getOperatorId()
    {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId)
    {
        this.operatorId = operatorId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getOrderStatus()
	{
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus)
	{
		this.orderStatus = orderStatus;
	}

	public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getDeliverStatus() {
        return deliverStatus;
    }

    public void setDeliverStatus(Integer deliverStatus) {
        this.deliverStatus = deliverStatus;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Integer facilityId) {
        this.facilityId = facilityId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getRemake() {
        return remake;
    }

    public void setRemake(String remake) {
        this.remake = remake;
    }

    public Double getPrincipal()
    {
        return principal;
    }

    public void setPrincipal(Double principal)
    {
        this.principal = principal;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPromotionId()
    {
        return promotionId;
    }

    public void setPromotionId(Integer promotionId)
    {
        this.promotionId = promotionId;
    }

    public Integer getBusinessId()
    {
        return businessId;
    }

    public void setBusinessId(Integer businessId)
    {
        this.businessId = businessId;
    }

    public String getDiscountMoney()
    {
        return discountMoney;
    }

    public void setDiscountMoney(String discountMoney)
    {
        this.discountMoney = discountMoney;
    }

    public List<ScPresaleInfo> getScPresaleInfoList() {
        return scPresaleInfoList;
    }

    public void setScPresaleInfoList(List<ScPresaleInfo> scPresaleInfoList) {
        this.scPresaleInfoList = scPresaleInfoList;
    }

    public Integer getIsAch()
    {
        return isAch;
    }

    public void setIsAch(Integer isAch)
    {
        this.isAch = isAch;
    }

	public Integer getIsRebate()
	{
		return isRebate;
	}

	public void setIsRebate(Integer isRebate)
	{
		this.isRebate = isRebate;
	}

	public Integer getCashId()
	{
		return cashId;
	}

	public void setCashId(Integer cashId)
	{
		this.cashId = cashId;
	}

    public Integer getAppraise() {
        return appraise;
    }

    public void setAppraise(Integer appraise) {
        this.appraise = appraise;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

	public Integer getSpecified() {
		return specified;
	}

	public void setSpecified(Integer specified) {
		this.specified = specified;
	}
    
}
