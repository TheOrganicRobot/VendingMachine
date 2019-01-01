package com.techelevator.classes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import com.techelevator.classes.Snack;

//-------This class contains the following methods---------//

// readInventoryListAndPopulate();
// displayStock();
// currentBalance();
// selectProduct();
// getSnack();
// withdrawMoney();
// selectProductHelperMethod();
// finishTransaction();
// logFile();
// salesReport();

public class Utility {
	// --------------------------------------------------------------------------------------
	public Map<String, Snack> readInventoryListAndPopulate() {
		Scanner scanner = null;

		Map<String, Snack> itemMap = new TreeMap<String, Snack>();

		File fileName = new File("vendingmachine.csv");
		try {
			scanner = new Scanner(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (scanner.hasNextLine()) {
			String firstLine = scanner.nextLine();
			String[] inventoryList = firstLine.split("\\|");

			Double price = Double.parseDouble(inventoryList[2]);
			itemMap.put(inventoryList[0], (new Snack(inventoryList[0], price, inventoryList[1])));
		}
		return itemMap;
	}

	// --------------------------------------------------------------------------------------
	public void displayStock(Map<String, Snack> itemMap) {
		System.out.println();
		for (Snack value : itemMap.values()) {
			if (value.getStock() < 1) {
				String format = "%1$-3s%2$-20s%3$-14s" + "Price: $" + "%4$-3.2f\n";
				System.out.format(format, value.getLocation(), value.getProductName(), "Qty: SOLD OUT",
						value.getPrice());
			} else {
				String format = "%1$-3s%2$-20s%3$-14s" + "Price: $" + "%4$-3.2f\n";
				System.out.format(format, value.getLocation(), value.getProductName(), "Qty: " + value.getStock(),
						value.getPrice());
			}
		}
		System.out.println();
	}

	// --------------------------------------------------------------------------------------
	public double moneyReceived(double currentBalance, Map<String, Snack> itemMap) {
		Scanner scanner = new Scanner(System.in);
		double money;
		double parseMoney;
		String response;

		while (true) {
			System.out.println();
			System.out.print("Please enter your money in whole dollar amounts ($1, $2, $5, $10): ");
			response = scanner.nextLine();
			System.out.println();

			if ((!response.equals("1")) && (!response.equals("2")) && (!response.equals("5"))
					&& (!response.equals("10"))) {
				System.out.println("\n*** " + response + " is not a valid option ***\n");
			} else {
				break;
			}
		}
		parseMoney = Double.parseDouble(response);
		money = currentBalance + parseMoney;
		logFile("FEED MONEY:", parseMoney, money);
		return money;
	}

	// --------------------------------------------------------------------------------------
	public String selectProduct(Map<String, Snack> itemMap) {
		Scanner scanner = new Scanner(System.in);
		String row;
		String column;

		System.out.println();
		for (Snack value : itemMap.values()) {
			if (value.getStock() < 1) {
				String format = "%1$-3s%2$-20s%3$-14s" + "Price: $" + "%4$-3.2f\n";
				System.out.format(format, value.getLocation(), value.getProductName(), "Qty: SOLD OUT",
						value.getPrice());
			} else {
				String format = "%1$-3s%2$-20s%3$-14s" + "Price: $" + "%4$-3.2f\n";
				System.out.format(format, value.getLocation(), value.getProductName(), "Qty: " + value.getStock(),
						value.getPrice());
			}
		}
		while (true) {
			System.out.println();
			System.out.print("Please select a row (A-D): ");
			row = scanner.nextLine().toUpperCase();

			if ((!row.equals("A")) && (!row.equals("B")) && (!row.equals("C")) && (!row.equals("D"))) {
				System.out.println("\n*** " + row + " is not a valid option ***\n");
			} else {
				break;
			}
		}
		while (true) {
			System.out.print("Please select a column (1-4): ");
			column = scanner.nextLine().toUpperCase();

			if ((!column.equals("1")) && (!column.equals("2")) && (!column.equals("3")) && (!column.equals("4"))) {
				System.out.println("\n*** " + column + " is not a valid option ***\n");
			} else {
				System.out.println();
				break;
			}
		}
		return row + column;
	}

	// --------------------------------------------------------------------------------------
	public boolean getSnack(String snackChoice, Map<String, Snack> itemMap, double currentBalance) {

		if (itemMap.get(snackChoice).getPrice() > currentBalance) {
			System.out.println("\nSorry, you don't have enough money.");
			System.out.printf("You only have have $%.2f%s", currentBalance, ".\n");
			System.out.println("You need at least $" + itemMap.get(snackChoice).getPrice() + " to purchase "
					+ itemMap.get(snackChoice).getProductName() + ".\n");
			return false;
		}

		if (itemMap.get(snackChoice).getStock() == 0) {
			System.out.println("Sorry, sold out.");
			return false;
		} else {
			itemMap.get(snackChoice).stockAdjust();
		}
		if (snackChoice.contains("A")) {
			System.out.println("Here is your " + itemMap.get(snackChoice).getProductName() + ".");
			System.out.println("Crunch Crunch, Yum!");
		}
		if (snackChoice.contains("B")) {
			System.out.println("Here is your " + itemMap.get(snackChoice).getProductName() + ".");
			System.out.println("Munch Munch, Yum!");
		}
		if (snackChoice.contains("C")) {
			System.out.println("Here is your " + itemMap.get(snackChoice).getProductName() + ".");
			System.out.println("Glug Glug, Yum!");
		}
		if (snackChoice.contains("D")) {
			System.out.println("Here is your " + itemMap.get(snackChoice).getProductName() + ".");
			System.out.println("Chew Chew, Yum!");
		}
		logFile(itemMap.get(snackChoice).getProductName() + " " + itemMap.get(snackChoice).getLocation(),
				currentBalance, currentBalance - itemMap.get(snackChoice).getPrice());
		salesReport(itemMap.get(snackChoice).getProductName(), itemMap.get(snackChoice).getPrice(), itemMap);
		return true;
	}

	// --------------------------------------------------------------------------------------
	public double withdrawMoney(double currentBalance, Map<String, Snack> itemMap, String snackChoice) {

		double withdrawAmount = itemMap.get(snackChoice).getPrice();
		return currentBalance -= withdrawAmount;
	}

	// --------------------------------------------------------------------------------------
	public double selectProductHelperMethod(double currentBalance, String snackChoice, Map<String, Snack> itemMap,
			Utility utility) {

		if (currentBalance == 0) {
			System.out.println("\nSorry, you have not inserted any money.");
			System.out.println("Please insert some money.\n");

		} else if (currentBalance > 0) {
			snackChoice = utility.selectProduct(itemMap);
			boolean canMakeTransaction = utility.getSnack(snackChoice, itemMap, currentBalance);
			if (canMakeTransaction == true) {
				currentBalance = utility.withdrawMoney(currentBalance, itemMap, snackChoice);
			}
		}
		return currentBalance;
	}

	// --------------------------------------------------------------------------------------
	public double finishTransaction(double currentBalance) {
		String quarter = "";
		String dime = "";
		String nickel = "";
		String penny = "";
		int getChangeBalance = (int) ((currentBalance + .001) * 100d);
		int quarters = getChangeBalance / 25;
		int modShift = getChangeBalance % 25;
		int dimes = modShift / 10;
		modShift = modShift % 10;
		int nickels = modShift / 5;
		modShift = modShift % 5;
		int pennies = modShift / 1;

		if (quarters == 1) {
			quarter = quarters + " quarter ";
		}
		if (quarters > 1) {
			quarter = quarters + " quarters ";
		}
		if (dimes == 1) {
			dime = dimes + " dime ";
		}
		if (dimes > 1) {
			dime = dimes + " dimes ";
		}
		if (nickels == 1) {
			nickel = nickels + " nickel ";
		}
		if (nickels > 1) {
			nickel = nickels + " nickels ";
		}
		if (pennies == 1) {
			penny = pennies + " penny ";
		}
		if (pennies > 1) {
			penny = pennies + " pennies ";
		}

		if (currentBalance > 0) {
			System.out.println("Your change is " + quarter + dime + nickel + penny);
		}
		System.out.println("Thank you for your business.");
		logFile("GIVE CHANGE:", getChangeBalance / 100d + .001, 0.00);
		return currentBalance = 0;
	}

	// --------------------------------------------------------------------------------------
	public void logFile(String logMessage, double currentBalance, double afterChange) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		File logFile = new File("log.txt");
		if (!logFile.exists()) {
			try {
				logFile.createNewFile();
			} catch (IOException e) {
				System.out.println("Cannot create log file.");
			}
		} else {
			FileWriter fstream;
			try {

				fstream = new FileWriter(logFile, true);
				BufferedWriter out = new BufferedWriter(fstream);

				out.write(String.format("%1$-23s%2$-23s" + "$" + "%3$-7.2f" + "$" + "%4$-6.2f", sdf.format(timestamp), logMessage, currentBalance, afterChange));
				out.newLine();
				out.close();
			} catch (IOException e) {
				System.out.println("Could not write to the file.");
			}
		}

		return;
	}

	// --------------------------------------------------------------------------------------
	public void salesReport(String productName, double itemPrice, Map<String, Snack> itemMap) {

		File salesReport = new File("salesreport.txt");
		Scanner fileIn = null;
		int allTimeSold = 0;
		if (!salesReport.exists()) {
			try {
				salesReport.createNewFile();
			} catch (IOException e) {
				System.out.println("File could not be created.");
			}
		}
		try {
			fileIn = new Scanner(salesReport);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (fileIn.hasNextLine()) {
			String thisLine = fileIn.nextLine();
			if (thisLine.contains("|")) {
				String name = thisLine.substring(0, thisLine.indexOf("|"));
				allTimeSold = Integer.parseInt(thisLine.substring(thisLine.indexOf("|") + 1));
				for (Snack snacks : itemMap.values()) {
					if (productName.equals(name)) {
						snacks.setAllTimeSold(allTimeSold);
					}
				}
			}
		}
		PrintWriter fileOut = null;
		try {
			fileOut = new PrintWriter(new FileWriter("salesreport.txt", false), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		double allTimeTotal = 0;
		for (Snack item : itemMap.values()) {
			fileOut.println(item.getProductName() + "|" + item.getAllTimeSold());
			allTimeTotal += item.getPrice() * item.getAllTimeSold();
		}
		fileOut.println();
		fileOut.println("**TOTAL SALES** $" + String.format("%#.2f", allTimeTotal));
	}
}
