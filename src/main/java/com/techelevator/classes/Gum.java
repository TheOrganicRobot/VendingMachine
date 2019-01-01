package com.techelevator.classes;

public class Gum extends Snack {

	public Gum(String location, double price, String productName) {
		super(location, price, productName);
	}

	@Override
	public void stockAdjust() {
		this.setStock(this.getStock() - 1);
		
	}



}
 