package test.java;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import dataprovider.Data;

public class Draft {
	private static WebDriver driver;
	private static String URL_MATCH = "e.mail.ru/messages/drafts/";
	public static final String DRAFTS_URL = "https://e.mail.ru/messages/drafts/";
	private static final String SAVED_MAIL = "(//div[@class='b-datalist__item__addr'])[1]";
	public static final String NO_LETTER_IN_THE_DRAFT_BOX_MESSAGE = "There is no letter in the draft box";
	private static final String CHECKBOX_BOX_ALL_LOCATOR = "div.b-checkbox__box";
	protected static final By LOGOUT_LINK_LOCATOR = By.id("PH_logoutLink");
	protected static final String LOGOUT_LINK = "x-ph__auth";

	private static final String REMOVE_BUTTON_LOCATOR = "//*[@data-name='remove']";


	@FindBy(css = CHECKBOX_BOX_ALL_LOCATOR)
	private WebElement checkBox;

	@FindBy(xpath = REMOVE_BUTTON_LOCATOR)
	private WebElement removeButton;

	@FindBy(className = LOGOUT_LINK)
	private WebElement logout;

	@FindBy(xpath = SAVED_MAIL)
	private WebElement savedMail;

	public Draft(WebDriver driver) {

		PageFactory.initElements(driver, this);
		this.driver = driver;
	}

	// public void draftCheck(Data data) {
	// driver.get(DRAFTS_URL);
	// driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	// String savedMail = SAVED_MAIL;
	// String subj = data.getSubject();
	// String address= data.getAddress();
	// String body= data.getBody();;
	// Assert.assertTrue(!(driver.findElements(By.xpath("//div[contains(text(),'"
	// + subj + "')]")).isEmpty()), NO_LETTER_IN_THE_DRAFT_BOX_MESSAGE);
	// Assert.assertTrue(!(driver.findElements(By.xpath("//div[contains(text(),'"
	// + address + "')]")).isEmpty()), NO_LETTER_IN_THE_DRAFT_BOX_MESSAGE);
	// Assert.assertTrue(!(driver.findElements(By.xpath("//span[contains(text(),'"
	// + body.substring(0, 20) + "')]")).isEmpty()),
	// NO_LETTER_IN_THE_DRAFT_BOX_MESSAGE);
	// }
	public void clickDraft(Data data) {
		driver.get(DRAFTS_URL);
		String subj = data.getSubject();
		driver.findElement(By.xpath("//div[contains(text(),'" + subj + "')][1]")).click();
	}

	public void cleanUpDraft() {

		driver.get(DRAFTS_URL);
		if (!checkBox.isSelected()) {
			checkBox.click();
		}

		removeButton.click();

	}

	public void logOut() throws InterruptedException {
		highlightElement(driver, logout);
		logout.click();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		Assert.assertTrue(!(isElementPresent((By) logout)));
		driver.close();

	}

	public void highlightElement(WebDriver driver, WebElement element) throws InterruptedException {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].style.color='red'", element);
		Thread.sleep(500);
		js.executeScript("arguments[0].style.color='grey'", element);
		Thread.sleep(500);
		js.executeScript("arguments[0].style.color='red'", element);
		Thread.sleep(500);
		js.executeScript("arguments[0].style.color='grey'", element);
	}

	public static boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
}
