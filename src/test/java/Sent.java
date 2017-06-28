package test.java;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import dataprovider.Data;

public class Sent {

	private static WebDriver driver;
	// private static String URL_MATCH = "e.mail.ru/messages/sent/";
	public static final String URL_SENT = "https://e.mail.ru/messages/sent/";
	private static final String CHECKBOX_BOX_ALL_LOCATOR = "div.b-checkbox__box";
	private static final String REMOVE_BUTTON_LOCATOR = "//*[@data-name='remove']";
	

	@FindBy(css = CHECKBOX_BOX_ALL_LOCATOR)
	private WebElement checkBox;

	@FindBy(xpath = REMOVE_BUTTON_LOCATOR)
	private WebElement removeButton;

	public Sent(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}

	// @Test(dataProvider = "mailData")
	public void checkIsSent(Data data) {
		String subj = data.getSubject();
		driver.get(URL_SENT);
		

		Assert.assertTrue(!(driver.findElements(By.xpath("//div[contains(text(),'" + subj + "')]")).isEmpty()),
				"There is no letter wasn`t sent");

	}

	public void cleanUpSent() {

		driver.get(URL_SENT);
		if (!checkBox.isSelected()) {
			checkBox.click();
		}

		removeButton.click();

	}
}
