package com.course.kafka.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

	@Column(nullable = false, length = 20)
	private String creditCardNumber;

	@OneToMany(mappedBy = "order")
	private List<OrderItem> items;

	@Column(nullable = false)
	private LocalDateTime orderDateTime;

	@Id
	@GeneratedValue
	private int orderId;

	@Column(nullable = false, length = 200)
	private String orderLocation;

	@Column(nullable = false, length = 20)
	private String orderNumber;

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public LocalDateTime getOrderDateTime() {
		return orderDateTime;
	}

	public int getOrderId() {
		return orderId;
	}

	public String getOrderLocation() {
		return orderLocation;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

	public void setOrderDateTime(LocalDateTime orderDateTime) {
		this.orderDateTime = orderDateTime;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public void setOrderLocation(String orderLocation) {
		this.orderLocation = orderLocation;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	@Override
	public String toString() {
		return "Order [items=" + items + ", orderDateTime=" + orderDateTime + ", creditCardNumber=" + creditCardNumber
				+ ", orderId=" + orderId + ", orderLocation=" + orderLocation + ", orderNumber=" + orderNumber + "]";
	}

}
