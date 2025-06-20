package jp.co.aforce.beans;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Purchase {
	private int purchaseId;
    private int lineNo;
    private String memberId;
    private LocalDate date;
    private int purchaseAmount;
    private LocalDate billingPeriod;
    private int productId;
    private int productAmount;
    private LocalDateTime createTime;
	public int getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(int purchaseId) {
		this.purchaseId = purchaseId;
	}
	public int getLineNo() {
		return lineNo;
	}
	public void setLineNo(int lineNo) {
		this.lineNo = lineNo;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public int getPurchaseAmount() {
		return purchaseAmount;
	}
	public void setPurchaseAmount(int purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}
	public LocalDate getBillingPeriod() {
		return billingPeriod;
	}
	public void setBillingPeriod(LocalDate billingPeriod) {
		this.billingPeriod = billingPeriod;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getProductAmount() {
		return productAmount;
	}
	public void setProductAmount(int productAmount) {
		this.productAmount = productAmount;
	}
	public LocalDateTime getCreateTime() {
		return createTime;
	}
	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

}
