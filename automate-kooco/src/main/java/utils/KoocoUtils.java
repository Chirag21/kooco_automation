package utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class KoocoUtils {
	private static final int TOTAL_AVAILABLE_ORDERS = 30;
	private static String popupText;
	private static String balancePageurl;

	public static String ordersGrabbed;
	public static WebDriver driver;
	public static WebDriverWait wait;

	protected KoocoUtils() {
	}

	public static void setup() {
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		int[] resolution = ConfigHelper.getScreenSize(); // returns width and height of screen resolution
		options.addArguments("--window-size=" + resolution[0] + "," + resolution[1], "--headless");
		driver = new ChromeDriver(options);
		balancePageurl = ConfigHelper.getProperty("kooco.balance.page");
		driver.get(ConfigHelper.getProperty("kooco.url"));
		wait = new WebDriverWait(driver, 20000);
	}

	public static String getBalancePageurl() {
		return balancePageurl;
	}

	public static void login() {
		driver.findElement(By.cssSelector(Locators.USER_NAME.toString()))
				.sendKeys(ConfigHelper.getProperty("kooco.username"));
		driver.findElement(By.cssSelector(Locators.PASSWORD.toString())).sendKeys(ConfigHelper.getProperty("kooco.password"));
		halt(1000);
		driver.findElement(By.cssSelector(Locators.LOGIN_BTN.toString())).click();
	}

	public static void removePopup() {
		setPopupText();
		driver.findElement(By.cssSelector(Locators.FIRST_POPUP.toString())).click();
	}

	public static void setPopupText() {
		popupText = driver.findElement(By.cssSelector(Locators.POPUP_CONTENT.toString())).getText();
	}

	public static String getPopupText() {
		return popupText;
	}

	public static void halt(long milliSeconds) {
		try {
			Thread.sleep(milliSeconds);
			System.out.println("Waiting.....");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void clickOnKoocoLogo() {
		driver.findElement(By.cssSelector(Locators.KOOCO_LOGO.toString())).click();
	}
	
	public static void clickOnRespectiveLevel() {
		String level = ConfigHelper.getProperty("kooco.level");
		switch (level) {
		case "LV1":
			driver.findElement(By.cssSelector(Locators.LEVEL_ONE.toString())).click();
			System.out.println("Clicked on LEVEL 1");
			break;
		case "LV2":
			driver.findElement(By.cssSelector(Locators.LEVEL_TWO.toString())).click();
			System.out.println("Clicked on LEVEL 2");
			break;
		case "LV3":
			driver.findElement(By.cssSelector(Locators.LEVEL_THREE.toString())).click();
			System.out.println("Clicked on LEVEL 3");
			break;
		case "LV4":
			driver.findElement(By.cssSelector(Locators.LEVEL_FOUR.toString())).click();
			System.out.println("Clicked on LEVEL 4");
			break;
		case "LV5":
			driver.findElement(By.cssSelector(Locators.LEVEL_FIVE.toString())).click();
			System.out.println("Clicked on LEVEL 5");
			break;
		default:
			System.err.println("Level not found!!!");
			tearDown();
			break;
		}
	}

	public static int getFrozenOrdersCount() {
		String frozenOrdersCount = driver.findElement(By.cssSelector(Locators.FROZEN_ORDERS.toString())).getText()
				.trim();
		return Integer.parseInt(frozenOrdersCount);
	}

	public static int getAvailableOrdersCount() {
		String availableOrdersCount = driver.findElement(By.cssSelector(Locators.TOTAL_ORDERS_GRABBED_TODAY.toString()))
				.getText().trim();
		return (TOTAL_AVAILABLE_ORDERS - Integer.parseInt(availableOrdersCount));
	}

	public static String getTotalOrdersGrabbedToday() {
		return driver.findElement(By.cssSelector(Locators.TOTAL_ORDERS_GRABBED_TODAY.toString())).getText().trim();
	}

	public static void grabOrder() {

		driver.findElement(By.cssSelector(Locators.START_TO_GRAB_ORDER_BTN.toString())).click();
	}

	public static void submitOrder() {
		driver.findElement(By.cssSelector(Locators.SUBMIT_BTN.toString())).click();
	}

	public static void waitForElementToBeClickable(String locator) {
		System.out.println("Waiting for element .....");
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(locator)));
	}

	public static void waitForElementToBeVisible(String locator) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locator)));
	}

	public static void tearDown() {
		driver.quit();
	}

	public static String getCurrentBalance() {
		return driver.findElement(By.cssSelector(Locators.CURRENT_BALANCE.toString())).getText().trim();
	}

	public static String getAvailableForWithdrawal() {
		return driver.findElement(By.cssSelector(Locators.AVAILABLE_FOR_WITHDRAWAL.toString())).getText().trim();
	}

	public static String getTodayRevenue() {
		return driver.findElement(By.cssSelector(Locators.TODAY_REVENUE.toString())).getText().trim().replace("₹", "");
	}

	public static String getTotalRevenue() {
		return driver.findElement(By.cssSelector(Locators.TOTAL_REVENUE.toString())).getText().trim().replace("₹", "");
	}

	public static String getOrderProfitToday() {
		return driver.findElement(By.cssSelector(Locators.ORDER_PROFIT_TODAY.toString())).getText().trim().replace("₹", "");
	}

	public static String getTotalMoney() {
		return driver.findElement(By.cssSelector(Locators.TOTAL_MONEY.toString())).getText().trim().replace("₹", "").replace("₹", "");
	}

	public static String getTodayTeamCommission() {
		return driver.findElement(By.cssSelector(Locators.TODAY_TEAM_COMMISSION.toString())).getText().trim().replace("₹", "");
	}

	public static String getTotalTeamCommission() {
		return driver.findElement(By.cssSelector(Locators.TOTAL_TEAM_COMMISSION.toString())).getText().trim().replace("₹", "");
	}

	public static List<String> getDataForExcel() {
		List<String> excelData = new ArrayList<>();
		String todayProfit = null;
		if (ExcelUtils.getYesterdayRevenue() != "0") {
			BigDecimal todayRevenue = new BigDecimal(getTodayRevenue().trim().replace("₹", ""));
			BigDecimal yesterdaYRevenue = new BigDecimal(ExcelUtils.getYesterdayRevenue());
			todayProfit = String.valueOf(todayRevenue.subtract(yesterdaYRevenue, MathContext.DECIMAL32).doubleValue());
		}
		else {
			todayProfit = getTodayRevenue().trim().replace("₹", "");
		}
		excelData.add(Calendar.getInstance().getTime().toString());
		excelData.add(getAvailableForWithdrawal());
		excelData.add(getTodayRevenue());
		excelData.add(todayProfit);
		excelData.add(getTotalRevenue());
		excelData.add(getOrderProfitToday());
		excelData.add(getTotalMoney());
		excelData.add(getTodayTeamCommission());
		excelData.add(getTotalTeamCommission());
		excelData.add(getCurrentBalance());
		excelData.add(ordersGrabbed);

		return excelData;
	}
}
