package com.techelevator.classes;

public class Candy extends Snack {

	public Candy(String location, double price, String productName) {
		super(location, price, productName);
	}

	@Override
	public void stockAdjust() {
		this.setStock(this.getStock() - 1);
		
	}

	

	
	
}
