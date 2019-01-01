package com.techelevator;

import com.techelevator.view.Menu;
import com.techelevator.classes.Snack;
import com.techelevator.classes.Utility;
import java.util.List;
import java.util.Map;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE };
	private static final String SECONDARY_MENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String SECONDARY_MENU_OPTION_SELECT_PRODUCT = "Select Product";
	private static final String SECONDARY_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
	private static final String[] SECONDARY_MENU_OPTIONS = { SECONDARY_MENU_OPTION_FEED_MONEY,
			SECONDARY_MENU_OPTION_SELECT_PRODUCT, SECONDARY_MENU_OPTION_FINISH_TRANSACTION };
	double currentBalance = 0;
	private Menu menu;

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	private void run() {

		Utility utility = new Utility();
		String snackChoice = null;

		Map<String, Snack> itemMap = utility.readInventoryListAndPopulate();

		while (true) {
			while (true) {
				String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS, currentBalance);

				if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
					utility.displayStock(itemMap);

				} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
					System.out.println();
					break;
				}
			}
			while (true) {
				String choice = (String) menu.getChoiceFromOptions(SECONDARY_MENU_OPTIONS, currentBalance);

				if (choice.equals(SECONDARY_MENU_OPTION_FEED_MONEY)) {

					currentBalance = utility.moneyReceived(currentBalance, itemMap);

				} else if (choice.equals(SECONDARY_MENU_OPTION_SELECT_PRODUCT)) {
					currentBalance = utility.selectProductHelperMethod(currentBalance, snackChoice, itemMap, utility);

				} else if (choice.startsWith(SECONDARY_MENU_OPTION_FINISH_TRANSACTION)) {
					currentBalance = utility.finishTransaction(currentBalance);

					break;
				}
			}
		}
	}

	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}
}
