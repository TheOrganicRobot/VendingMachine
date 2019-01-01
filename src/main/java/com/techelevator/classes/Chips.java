package com.techelevator.classes;

public class Chips extends Snack {

	public Chips(String location, double price, String productName) {
		super(location, price, productName);
	}

	@Override
	public void stockAdjust() {
		this.setStock(this.getStock() - 1);
		
	}

	

}
