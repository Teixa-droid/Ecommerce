package com.shop.ecommerce.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String number;
	private Date created_at;
	private Date received_at;

	private double total;

	@ManyToOne
	private User user;

	@OneToMany(mappedBy = "order")
	private List<OrderDetail> detail;

	public Order() {
		// TODO Auto-generated constructor stub
	}

	public Order(Integer id, String number, Date created_at, Date received_at, double total) {
		super();
		this.id = id;
		this.number = number;
		this.created_at = created_at;
		this.received_at = received_at;
		this.total = total;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getReceived_at() {
		return received_at;
	}

	public void setReceived_at(Date received_at) {
		this.received_at = received_at;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<OrderDetail> getDetail() {
		return detail;
	}

	public void setDetail(List<OrderDetail> detail) {
		this.detail = detail;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", number=" + number + ", created_at=" + created_at + ", received_at=" + received_at
				+ ", total=" + total + "]";
	}

}
