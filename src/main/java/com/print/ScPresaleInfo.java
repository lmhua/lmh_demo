package com.print;

import java.io.Serializable;
import java.util.Date;

/**
 * 定金预购商品/服务
 * @author PengHuali
 *
 */
public class ScPresaleInfo implements Serializable {

	/**
	 * 版本序列化id
	 */
	private static final long serialVersionUID = 6879284769196080174L;
	
	/**
	 * 主键id
	 */
	private Integer id;
	
	/**
	 * 商品/基本服务id
	 */
	private Integer goodsServiceId;
	
	/**
	 * 预售id
	 */
	private Integer presaleId;
	
	/**
	 * 购买商品/服务名
	 */
	private String purchaseName;
	
	/**
	 * 购买类型（0-商品 1-服务）
	 */
	private Integer purchaseType;
	
	/**
	 * 购买数量
	 */
	private Integer purchaseNum;
	
	/**
	 * 单价
	 */
	private Double unitPrice;
	
	/**
	 * 实际支付
	 */
	private Double actualPayment;
	
	/**
	 * 是否店面留用（0-否 1-是）
	 */
	private Integer storeRetention;
	
	/**
   * 创建人编号
   */
  private Integer createId;
  
  /**
   * 创建人姓名
   */
  private String createName;
  
  /**
   * 备注
   */
  private String remake;
  
  /**
   * 是否打印（0：未打印   1：已打印）
   */
  private Integer isPrint;
  
  /**
   * 商品规格id
   */
  private Integer stockId;
  
  /**
   * 后期添加的字段
   * @return
   */
  private Integer waiterId;
  private String waiterName;
  private Integer makeStatus;
  private Date consumeTime;

	public Integer getWaiterId() {
    return waiterId;
}

public void setWaiterId(Integer waiterId) {
    this.waiterId = waiterId;
}

public String getWaiterName() {
    return waiterName;
}

public void setWaiterName(String waiterName) {
    this.waiterName = waiterName;
}

public Integer getMakeStatus() {
    return makeStatus;
}

public void setMakeStatus(Integer makeStatus) {
    this.makeStatus = makeStatus;
}

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGoodsServiceId() {
		return goodsServiceId;
	}

	public void setGoodsServiceId(Integer goodsServiceId) {
		this.goodsServiceId = goodsServiceId;
	}

	public Integer getPresaleId() {
		return presaleId;
	}

	public void setPresaleId(Integer presaleId) {
		this.presaleId = presaleId;
	}

	public String getPurchaseName() {
		return purchaseName;
	}

	public void setPurchaseName(String purchaseName) {
		this.purchaseName = purchaseName;
	}

	public Integer getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(Integer purchaseType) {
		this.purchaseType = purchaseType;
	}

	public Integer getPurchaseNum() {
		return purchaseNum;
	}

	public void setPurchaseNum(Integer purchaseNum) {
		this.purchaseNum = purchaseNum;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getActualPayment() {
		return actualPayment;
	}

	public void setActualPayment(Double actualPayment) {
		this.actualPayment = actualPayment;
	}

	public Integer getStoreRetention() {
		return storeRetention;
	}

	public void setStoreRetention(Integer storeRetention) {
		this.storeRetention = storeRetention;
	}
	
	public Integer getCreateId() {
    return createId;
  }

  public void setCreateId(Integer createId) {
    this.createId = createId;
  }
  
  public String getCreateName() {
    return createName;
  }

  public void setCreateName(String createName) {
    this.createName = createName;
  }
  
  public String getRemake() {
    return remake;
  }

  public void setRemake(String remake) {
    this.remake = remake;
  }
  
  public Integer getIsPrint() {
    return isPrint;
  }

  public void setIsPrint(Integer isPrint) {
    this.isPrint = isPrint;
  }
	
  public Integer getStockId()
  {
	return stockId;
  }
	
  public void setStockId(Integer stockId)
  {
	this.stockId = stockId;
  }

public Date getConsumeTime() {
    return consumeTime;
}

public void setConsumeTime(Date consumeTime) {
    this.consumeTime = consumeTime;
}

}
