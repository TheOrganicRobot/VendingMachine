package com.techelevator.classes;

public class Beverage extends Snack {

	public Beverage(String location, double price, String productName) {
		super(location, price, productName);
	}

	@Override
	public void stockAdjust() {
		this.setStock(this.getStock() - 1);
		
	}


	
	
}
