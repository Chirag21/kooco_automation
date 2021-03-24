package kooco;

import java.util.List;

import enums.ConfigProperties;
import enums.Locators;
import utils.PropertyUtils;
import utils.ExcelUtils;
import utils.KoocoUtils;

public class Runner extends KoocoUtils {

	public static int availableOrdersCount = 0;
	public static int frozenOrdersCount = 0;

	public static void main(String[] args) {
		setup();
		login();

		// remove only first popup which appears after login
		waitForElementToBeVisible(Locators.POPUP_CONTENT.toString());
		removePopup();
		clickOnRespectiveLevel();
		while (true) {
			waitForElementToBeClickable(Locators.START_TO_GRAB_ORDER_BTN.toString());
			System.out.println("On main page");
			halt(1500);
			availableOrdersCount = getAvailableOrdersCount();
			frozenOrdersCount = getFrozenOrdersCount();
			System.out.println(availableOrdersCount);
			System.out.println(frozenOrdersCount);
			if (availableOrdersCount != 0 && frozenOrdersCount == 0) {
				grabOrder();
				System.out.println("Clicked on grab order");
				waitForElementToBeClickable(Locators.SUBMIT_BTN.toString());
				submitOrder();
				System.out.println("Clicked on submit order");
				waitForElementToBeClickable(Locators.START_TO_GRAB_ORDER_BTN.toString());
				halt(4000);
			} else {
				if (availableOrdersCount == 0) {
					System.out.println(
							"All orders are already grabbed. Available orders count : " + availableOrdersCount);

					break;
				}else if (frozenOrdersCount != 0) {
					System.out.println("Orders got frozen. Frozen orders count : " + frozenOrdersCount);
					break;
				}
				System.out.println("Unknown Error!!!");
			}
		}

		if ("Y".equalsIgnoreCase(PropertyUtils.getProperty(ConfigProperties.KOOCO_SAVE_FILE.name().toLowerCase()))) {
			// set ordersGrabbed for excel
			ordersGrabbed = getTotalOrdersGrabbedToday();
			driver.get(getBalancePageurl());
			waitForElementToBeVisible(Locators.AVAILABLE_FOR_WITHDRAWAL.toString());
			halt(2000);
			ExcelUtils.createOrBackup();
			List<String> data = getDataForExcel();
			ExcelUtils.writeToExcel(data);
		}
		System.out.println(
				"-----------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println(getPopupText());
		tearDown();
	}
}