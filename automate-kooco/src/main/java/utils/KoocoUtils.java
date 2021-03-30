package utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import enums.ConfigProperties;
import enums.Locators;
import io.github.bonigarcia.wdm.WebDriverManager;

public class KoocoUtils {
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
		int[] resolution = PropertyUtils.getScreenSize(); // returns width and height of screen resolution
		options.addArguments("--window-size=" + resolution[0] + "," + resolution[1], "--headless");
		// options.addArguments("--start-maximized");//, "--headless");
		driver = Objects.requireNonNull(new ChromeDriver(options), "driver is null!!!");
		balancePageurl = PropertyUtils.getProperty(ConfigProperties.KOOCO_BALANCE_PAGE_URL.name().toLowerCase());
		driver.get(PropertyUtils.getProperty(ConfigProperties.KOOCO_URL.name().toLowerCase()));
		wait = new WebDriverWait(driver, 20000);
	}

	public static String getBalancePageurl() {
		return balancePageurl;
	}

	public static void login() {
		driver.findElement(By.cssSelector(Locators.USER_NAME.toString()))
				.sendKeys(PropertyUtils.getProperty(ConfigProperties.KOOCO_USERNAME.name().toLowerCase()));

		driver.findElement(By.cssSelector(Locators.PASSWORD.toString()))
				.sendKeys(PropertyUtils.getProperty(ConfigProperties.KOOCO_PASSWORD.name().toLowerCase()));
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
		String level = PropertyUtils.getProperty(ConfigProperties.KOOCO_LEVEL.name().toLowerCase());
		switch (Objects.requireNonNull(level, "level returned null!!!")) {
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
		String frozenOrdersCount = driver.findElement(By.cssSelector(Locators.FROZEN_ORDERS.toString())).getText().trim();
		return Integer.parseInt(frozenOrdersCount);
	}

	public static int getAvailableOrdersCount() {
		String availableOrdersCount = driver.findElement(By.cssSelector(Locators.TOTAL_ORDERS_GRABBED_TODAY.toString())).getText().trim();
		int totalOrderCount = Integer
				.parseInt(PropertyUtils.getProperty(ConfigProperties.KOOCO_TOTAL_ORDER_COUNT.name().toLowerCase()));
		return (totalOrderCount - Integer.parseInt(availableOrdersCount));
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
		return driver.findElement(By.cssSelector(Locators.ORDER_PROFIT_TODAY.toString())).getText().trim().replace("₹","");
	}

	public static String getTotalMoney() {
		return driver.findElement(By.cssSelector(Locators.TOTAL_MONEY.toString())).getText().trim().replace("₹", "");
	}

	public static String getTodayTeamCommission() {
		return driver.findElement(By.cssSelector(Locators.TODAY_TEAM_COMMISSION.toString())).getText().trim().replace("₹", "");
	}

	public static String getTotalTeamCommission() {
		return driver.findElement(By.cssSelector(Locators.TOTAL_TEAM_COMMISSION.toString())).getText().trim().replace("₹", "");
	}

	public static List<String> getDataForExcel() {
		List<String> excelData = new ArrayList<>();
		StringBuffer todayProfit = new StringBuffer();
		if (!ExcelUtils.getYesterdayRevenue().equals("0")) {
			BigDecimal todayRevenue = new BigDecimal(getTodayRevenue().trim()); // .replace("₹", ""));
			BigDecimal yesterdayRevenue = new BigDecimal(ExcelUtils.getYesterdayRevenue());
			todayProfit = todayProfit.append(
					String.valueOf(todayRevenue.subtract(yesterdayRevenue, MathContext.DECIMAL32).doubleValue()));
		} else {
			todayProfit = todayProfit.append(getTodayRevenue().trim()); // .replace("₹", ""));
		}
		excelData.add(Calendar.getInstance().getTime().toString());
		excelData.add(getAvailableForWithdrawal());
		excelData.add(getTodayRevenue());
		excelData.add(todayProfit.toString());
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
