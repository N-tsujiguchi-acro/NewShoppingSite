package jp.co.aforce.beans;

import java.security.Timestamp;

public class Product {
	private Integer product_id;     // AUTO_INCREMENT
    private Integer category_id;
    private String name;
    private Integer price;
    private Integer quantity;
    private Timestamp updateTime;
    private Timestamp createTime;
    private String img_name;
    private int amount;
	public Integer getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}
	public Integer getCategory_id() {
		return category_id;
	}
	public void setCategory_id(Integer category_id) {
		this.category_id = category_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getImg() {
		return img_name;
	}
	public void setImg(String img_Name) {
		this.img_name = img_Name;
	}
	public int getAmount() {
	    return amount;
	}

	public void setAmount(int amount) {
	    this.amount = amount;
	}
    

}
