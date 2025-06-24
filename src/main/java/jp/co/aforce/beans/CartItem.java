package jp.co.aforce.beans;

import java.sql.Timestamp;

public class CartItem {
	private int id;
	private int cart_id;
	private int product_i;
	private int amount;
	private Timestamp created_at;
	private Timestamp updated_at;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCart_id() {
		return cart_id;
	}
	public void setCart_id(int cart_id) {
		this.cart_id = cart_id;
	}
	public int getProduct_i() {
		return product_i;
	}
	public void setProduct_i(int product_i) {
		this.product_i = product_i;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Timestamp getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}
	public Timestamp getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(Timestamp updated_at) {
		this.updated_at = updated_at;
	}
	

}
