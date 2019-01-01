package com.techelevator.classes;

public class Snack {

	private String productName;
	private String location;
	private double price;
	private int stock;
	private int allTimeSold;
	private int countSold;

	

	

	public Snack(String location, double price, String productName) {
		this.location = location;
		this.price = price;
		this.productName = productName;
		this.stock = 5;
	}
	
	public int getCountSold() {
		return countSold;
	}
	
	public int getAllTimeSold() {
		return allTimeSold;
	}

	public void setAllTimeSold(int numSold) {
		allTimeSold += numSold;
	}
	
	public void stockAdjust() {
		this.stock--;
		this.countSold++;
		this.allTimeSold++;
		
	}

	public String getProductName() {
		return productName;
	}

	public String getLocation() {
		return location;
	}

	public double getPrice() {
		return price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

}
